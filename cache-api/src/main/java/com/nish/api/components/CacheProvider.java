package com.nish.api.components;

import com.nish.api.exception.EntityNotFoundException;
import com.nish.api.exception.InvalidInputException;
import com.nish.api.model.CacheDefinition;
import com.nish.api.model.CacheDetail;
import com.nish.api.utils.AppStatusCode;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Provides utility methods for managing and interacting with caches in the application.
 * <p>
 * The {@code CacheProvider} facilitates operations such as retrieving cache definitions,
 * managing cache entries (CRUD operations), and interacting with multiple {@link CacheManager} instances.
 */
@Component
public class CacheProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(CacheProvider.class);
  private final Gson json;
  private final CacheConfigLoader cacheConfigLoader;

  private Map<String, CacheManager> cacheManagerMap = Collections.emptyMap();

  /**
   * Constructs a new {@code CacheProvider} with a map of {@link CacheManager} instances.
   *
   * @param cacheConfigLoader a map where keys are {@link CacheConfigLoader} names and values are {@link CacheManager} instances
   * @param json            instances which is used to parse the json content
   */
  public CacheProvider(@Qualifier("json") Gson json, CacheConfigLoader cacheConfigLoader) {
    this.json = json;
    this.cacheConfigLoader = cacheConfigLoader;
  }

  public void setCacheManagers(Map<String, CacheManager> cacheManagerMap) {
    this.cacheManagerMap = cacheManagerMap;
  }

  public void init() {
    setCacheManagers(cacheConfigLoader.loadCacheManagerMap());
  }

  /**
   * Retrieves the definition of a cache, including the key and value types.
   *
   * @param <K>              the type of keys used in the cache
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the {@link CacheManager} containing the cache
   * @param cacheAlias       the alias of the cache within the {@link CacheManager}
   * @return a {@link CacheDefinition} containing the key and value types of the cache
   * @throws EntityNotFoundException if the specified {@link CacheManager} or cache alias is not found
   */
  public <K, V> CacheDefinition getCacheDefinition(String cacheManagerName, String cacheAlias) {
    RequestValidator.validateInput(cacheManagerName, cacheAlias);
    CacheDetail<K, V> cacheDetail = getCacheDetails(cacheManagerName, cacheAlias);
    return new CacheDefinition(cacheDetail.keyType().getName(), cacheDetail.valueType().getName());
  }

  /**
   * Retrieves multiple entries from a cache by their keys.
   *
   * @param <K>              the type of keys used in the cache
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the {@link CacheManager} containing the cache
   * @param cacheAlias       the alias of the cache within the {@link CacheManager}
   * @param keys             a set of keys to retrieve from the cache
   * @return a map containing the requested key-value pairs
   * @throws EntityNotFoundException if the specified {@link CacheManager} or cache alias is not found
   */
  public <K, V> Map<K, V> getEntries(String cacheManagerName, String cacheAlias, Set<String> keys) {
    CacheDetail<K, V> cacheDetail = getCacheDetails(cacheManagerName, cacheAlias);
    Set<K> keysSet = keys.stream().map(e -> parseKey(e, cacheDetail.keyType())).collect(Collectors.toSet());
    return cacheDetail.cache().getAll(keysSet);
  }

  /**
   * Updates or adds multiple entries to a cache.
   *
   * @param <K>              the type of keys used in the cache
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the {@link CacheManager} containing the cache
   * @param cacheAlias       the alias of the cache within the {@link CacheManager}
   * @param cacheEntries     a map of entries to update or add to the cache
   * @throws EntityNotFoundException if the specified {@link CacheManager} or cache alias is not found
   */
  public <K, V> void updateEntries(String cacheManagerName, String cacheAlias, Map<String, V> cacheEntries) {
    CacheDetail<K, V> cacheDetail = getCacheDetails(cacheManagerName, cacheAlias);
    Map<K, V> parsedEntries = new HashMap<>(cacheEntries.size());
    for (Map.Entry<String, V> entry : cacheEntries.entrySet()) {
      K parsedKey = parseKey(entry.getKey(), cacheDetail.keyType());
      V parsedValue = parseValue(entry.getValue(), cacheDetail.valueType());
      parsedEntries.put(parsedKey, parsedValue);
    }
    cacheDetail.cache().putAll(parsedEntries);
  }

  /**
   * Deletes multiple entries from a cache by their keys.
   *
   * @param <K>              the type of keys used in the cache
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the {@link CacheManager} containing the cache
   * @param cacheAlias       the alias of the cache within the {@link CacheManager}
   * @param keys             a set of keys to delete from the cache
   * @throws EntityNotFoundException if the specified {@link CacheManager} or cache alias is not found
   */
  public <K, V> void deleteEntries(String cacheManagerName, String cacheAlias, Set<String> keys) {
    CacheDetail<K, V> cacheDetail = getCacheDetails(cacheManagerName, cacheAlias);
    Set<K> keysSet = keys.stream().map(e -> parseKey(e, cacheDetail.keyType())).collect(Collectors.toSet());
    cacheDetail.cache().removeAll(keysSet);
  }

  /**
   * Deletes a single entry from a cache by its key.
   *
   * @param <K>              the type of keys used in the cache
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the {@link CacheManager} containing the cache
   * @param cacheAlias       the alias of the cache within the {@link CacheManager}
   * @param key              the key of the entry to delete
   * @throws EntityNotFoundException if the key is not found in the cache
   */
  public <K, V> void deleteEntry(String cacheManagerName, String cacheAlias, String key) {
    CacheDetail<K, V> cacheDetail = getCacheDetails(cacheManagerName, cacheAlias);
    K parsedKey = parseKey(key, cacheDetail.keyType());
    cacheDetail.cache().remove(parsedKey);
  }

  /**
   * Retrieves the details of a cache, including its {@link Cache} instance and type information.
   *
   * @param <K>              the type of keys used in the cache
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the {@link CacheManager} containing the cache
   * @param cacheAlias       the alias of the cache within the {@link CacheManager}
   * @return a {@link CacheDetail} object containing cache and type information
   * @throws EntityNotFoundException if the {@link CacheManager} or cache alias is not found
   */
  @SuppressWarnings("unchecked")
  private <K, V> CacheDetail<K, V> getCacheDetails(String cacheManagerName, String cacheAlias) {
    RequestValidator.validateInput(cacheManagerName, cacheAlias);
    CacheManager cacheManager = cacheManagerMap.get(cacheManagerName);
    if (cacheManager == null) {
      LOGGER.error("CacheManager with name {} is not found.", cacheManagerName);
      throw new EntityNotFoundException(AppStatusCode.CACHEMANAGER_NOT_FOUND);
    }
    if (cacheManager.getRuntimeConfiguration().getCacheConfigurations().get(cacheAlias) == null) {
      LOGGER.error("Cache with cache-alias {} is not found.", cacheAlias);
      throw new EntityNotFoundException(AppStatusCode.CACHE_NOT_FOUND);
    }
    Class<K> keyType = (Class<K>) cacheManager.getRuntimeConfiguration().getCacheConfigurations().get(cacheAlias).getKeyType();
    Class<V> valueType = (Class<V>) cacheManager.getRuntimeConfiguration().getCacheConfigurations().get(cacheAlias).getValueType();
    Cache<K, V> cache = cacheManager.getCache(cacheAlias, keyType, valueType);
    return new CacheDetail<>(cache, keyType, valueType);
  }

  /**
   * Converts a JSON string into an object of the specified type.
   *
   * @param <T>        the type of the desired object
   * @param jsonString the JSON string to convert
   * @param clazz      the class of the desired object type
   * @return the clazz type representation of given JSON string
   */
  public <T> T parseKey(String jsonString, Class<T> clazz) {
    if (null == jsonString) {
      throw new InvalidInputException(AppStatusCode.INVALID_KEY, "Key is null.");
    }
    if (clazz.equals(String.class)) {
      return clazz.cast(jsonString);
    }
    try {
      T parsedKey = json.fromJson(jsonString, clazz);
      if (parsedKey == null) {
        throw new InvalidInputException(AppStatusCode.INVALID_KEY, "Invalid key.");
      }
      return parsedKey;
    } catch (RuntimeException e) {
      throw new InvalidInputException(AppStatusCode.INVALID_KEY, e);
    }
  }

  /**
   * Converts a value into an object of the specified type.
   *
   * @param <V>   the type of values stored in the cache
   * @param <T>   the type of the desired object
   * @param value the value to convert
   * @param clazz the class of the desired object type
   * @return the clazz type representation of given value
   */
  @SuppressWarnings("unchecked")
  public <V, T> T parseValue(V value, Class<T> clazz) {
    // first check for null value to discard early this use case
    if (value == null) {
      throw new InvalidInputException(AppStatusCode.INVALID_VALUE, "Value is null.");
    }

    // then check for boolean and string type: if the target type is boolean or string, json field must be the same
    if (clazz == Boolean.class || clazz == String.class) {
      try {
        return clazz.cast(value);
      } catch (ClassCastException e) {
        throw new InvalidInputException(AppStatusCode.INVALID_VALUE, e);
      }
    }

    // Special case first for BigDecimal and BigInteger that we need to handle before the more generic case of numbers.
    // These are not supported Json types: a Json field number will never be a BigDecimal or a BigInteger because Json does not support so big numbers.
    // So if the user has a cache with BigDecimal or BigInteger, he does not have a choice but to send the values as String.
    // The code below will support the conversion, even of value is not a String. So the user will be able to send a String for big numbers or a normal Json field number if the number is not big.
    if (clazz == BigInteger.class) {
      return clazz.cast(new BigInteger(value.toString(), 10));
    }
    if (clazz == BigDecimal.class) {
      return clazz.cast(new BigDecimal(value.toString()));
    }

    // left cases are normal numbers, which are more complex to parse
    // Integer.class, Long.class, Double.class are the possible types set at deserialization for a json field number depending if the value is big.
    // In any case, whatever the number, the Json field will be a number and then the deserialized Java value should be a number also: Integer.class, Long.class, Double.class.
    if (Number.class.isAssignableFrom(clazz)) {
      Number number;

      // we make sure the json contained a number field, if not we fail (string or boolean, we throw)
      try {
        number = Number.class.cast(value);
      } catch (ClassCastException e) {
        throw new InvalidInputException(AppStatusCode.INVALID_VALUE, e);
      }

      // Convert the number to the specified class type
      if (clazz == Integer.class) {
        return clazz.cast(number.intValue());
      } else if (clazz == Long.class) {
        return clazz.cast(number.longValue());
      } else if (clazz == Double.class) {
        return clazz.cast(number.doubleValue());
      } else if (clazz == Float.class) {
        return clazz.cast(number.floatValue());
      } else if (clazz == Short.class) {
        return clazz.cast(number.shortValue());
      } else if (clazz == Byte.class) {
        return clazz.cast(number.byteValue());
      } else {
        // we do not support non-standard java numbers
        throw new InvalidInputException(AppStatusCode.INVALID_VALUE, "Unsupported number type: " + clazz);
      }
    }

    try {
//      T parsedValue = json.map(value, clazz);
      T parsedValue = (T) json.toJsonTree(value, clazz);
      if (parsedValue == null) {
        throw new InvalidInputException(AppStatusCode.INVALID_VALUE, "Invalid value.");
      }
      return parsedValue;
    } catch (RuntimeException e) {
      throw new InvalidInputException(AppStatusCode.INVALID_VALUE, e);
    }
  }
}
