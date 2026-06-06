package com.nish.api.service;

import com.nish.api.components.CacheProvider;
import com.nish.api.model.CacheDefinition;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * Service class for managing cache operations.
 * <p>
 * This class acts as a bridge between the controller and the {@link CacheProvider}, handling
 * business logic related to cache operations, such as retrieving cache definitions,
 * managing cache entries, and deleting cache data.
 * </p>
 *
 */
@Service
public class CacheService {

  final CacheProvider cacheProvider;

  public CacheService(CacheProvider cacheProvider) {
    this.cacheProvider = cacheProvider;
  }

  /**
   * Retrieves the definition of a specific cache.
   *
   * @param cacheManagerName the name of the cache manager
   * @param cacheAlias       the alias of the cache
   * @return the {@link CacheDefinition} object containing metadata about the specified cache
   */
  public CacheDefinition getCacheDefinition(String cacheManagerName, String cacheAlias) {
    return cacheProvider.getCacheDefinition(cacheManagerName, cacheAlias);
  }

  /**
   * Retrieves specific entries from a cache based on provided keys.
   *
   * @param <K>              the type of keys used in the cache
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the cache manager
   * @param cacheAlias       the alias of the cache
   * @param keys             the set of keys whose values need to be retrieved
   * @return a {@link Map} containing key-value pairs for the specified keys
   */
  public <K, V> Map<K, V> getEntries(String cacheManagerName, String cacheAlias, Set<String> keys) {
    return cacheProvider.getEntries(cacheManagerName, cacheAlias, keys);
  }

  /**
   * Updates entries in the specified cache.
   *
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the cache manager
   * @param cacheAlias       the alias of the cache
   * @param cacheEntries     a {@link Map} containing the key-value pairs to update in the cache
   */
  public <V> void updateEntries(String cacheManagerName, String cacheAlias, Map<String, V> cacheEntries) {
    cacheProvider.updateEntries(cacheManagerName, cacheAlias, cacheEntries);
  }

  /**
   * Deletes specific entries from a cache.
   *
   * @param cacheManagerName the name of the cache manager
   * @param cacheAlias       the alias of the cache
   * @param keys             a set of keys representing the entries to delete
   */
  public void deleteEntries(String cacheManagerName, String cacheAlias, Set<String> keys) {
    cacheProvider.deleteEntries(cacheManagerName, cacheAlias, keys);
  }

  /**
   * Deletes a single entry from a cache based on the key.
   *
   * @param cacheManagerName the name of the cache manager
   * @param cacheAlias       the alias of the cache
   * @param key              the key of the entry to delete
   */
  public void deleteEntry(String cacheManagerName, String cacheAlias, String key) {
    cacheProvider.deleteEntry(cacheManagerName, cacheAlias, key);
  }

}
