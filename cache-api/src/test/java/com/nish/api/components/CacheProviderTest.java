/*
 * Copyright (c) 2024 Software AG, Darmstadt, Germany and/or Software AG USA Inc., Reston, VA, USA, and/or its subsidiaries and/or its affiliates and/or their licensors.
 * Use, reproduction, transfer, publication or disclosure is prohibited except as specifically provided for in your License Agreement with Software AG.
 */
package com.nish.api.components;

import com.google.gson.Gson;
import com.nish.api.exception.EntityNotFoundException;
import com.nish.api.exception.InvalidInputException;
import com.nish.api.model.CacheDefinition;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
class CacheProviderTest {

  private CacheProvider cacheProvider;
  private CacheConfigLoader cacheConfigLoader;

  private final String cacheManagerName = "cacheManager";
  private final String cacheAlias = "cache";

  @BeforeEach
  void setUp() {
    cacheConfigLoader = mock(CacheConfigLoader.class);
    cacheProvider = new CacheProvider(new Gson(), cacheConfigLoader);
  }

  private <K, V> Cache<K, V> wireCache(
          String managerName,
          String alias,
          Class<K> keyType,
          Class<V> valueType,
          Cache<K, V> cache) {

    CacheManager cacheManager = mock(CacheManager.class);
    Configuration configuration = mock(Configuration.class);
    Map<String, CacheConfiguration<?, ?>> configMap = mock(Map.class);
    CacheConfiguration<?, ?> cacheConfiguration = mock(CacheConfiguration.class);

    doReturn(Map.of(managerName, cacheManager)).when(cacheConfigLoader).loadCacheManagerMap();
    cacheProvider.init();

    doReturn(configuration).when(cacheManager).getRuntimeConfiguration();
    doReturn(configMap).when(configuration).getCacheConfigurations();
    doReturn(cacheConfiguration).when(configMap).get(eq(alias));
    doReturn(keyType).when(cacheConfiguration).getKeyType();
    doReturn(valueType).when(cacheConfiguration).getValueType();
    doReturn(cache).when(cacheManager).getCache(eq(alias), eq(keyType), eq(valueType));

    return cache;
  }

  @Test
  void getCacheDefinition_returnsTypes() {
    Cache<Integer, Long> cache = mock(Cache.class);
    wireCache(cacheManagerName, cacheAlias, Integer.class, Long.class, cache);

    CacheDefinition definition = cacheProvider.getCacheDefinition(cacheManagerName, cacheAlias);

    assertEquals("java.lang.Integer", definition.keyType());
    assertEquals("java.lang.Long", definition.valueType());
  }

  @Test
  void getEntries_returnsRequestedValues() {
    Cache<Integer, Long> cache = mock(Cache.class);
    wireCache(cacheManagerName, cacheAlias, Integer.class, Long.class, cache);

    doReturn(Map.of(1, 100L)).when(cache).getAll(eq(Set.of(1)));

    Map<Integer, Long> result = cacheProvider.getEntries(cacheManagerName, cacheAlias, Set.of("1"));

    assertEquals(1, result.size());
    assertEquals(100L, result.get(1));
    verify(cache).getAll(Set.of(1));
  }

  @Test
  void updateEntries_putsParsedValues() {
    Cache<Integer, Long> cache = mock(Cache.class);
    wireCache(cacheManagerName, cacheAlias, Integer.class, Long.class, cache);

    cacheProvider.updateEntries(cacheManagerName, cacheAlias, Map.of("1", 100L));

    verify(cache).putAll(Map.of(1, 100L));
  }

  @Test
  void deleteEntries_removesParsedKeys() {
    Cache<Integer, Long> cache = mock(Cache.class);
    wireCache(cacheManagerName, cacheAlias, Integer.class, Long.class, cache);

    cacheProvider.deleteEntries(cacheManagerName, cacheAlias, Set.of("1", "2"));

    verify(cache).removeAll(Set.of(1, 2));
  }

  @Test
  void deleteEntry_removesParsedKey() {
    Cache<Integer, Long> cache = mock(Cache.class);
    wireCache(cacheManagerName, cacheAlias, Integer.class, Long.class, cache);

    cacheProvider.deleteEntry(cacheManagerName, cacheAlias, "1");

    verify(cache).remove(1);
  }

  @Test
  void getCacheDefinition_throwsWhenCacheManagerMissing() {
    doReturn(Map.of()).when(cacheConfigLoader).loadCacheManagerMap();
    cacheProvider.init();

    assertThrows(EntityNotFoundException.class,
            () -> cacheProvider.getCacheDefinition("missingManager", cacheAlias));
  }

  @Test
  void getCacheDefinition_throwsWhenCacheAliasMissing() {
    CacheManager cacheManager = mock(CacheManager.class);
    Configuration configuration = mock(Configuration.class);
    Map<String, CacheConfiguration<?, ?>> configMap = mock(Map.class);

    doReturn(Map.of(cacheManagerName, cacheManager)).when(cacheConfigLoader).loadCacheManagerMap();
    cacheProvider.init();

    doReturn(configuration).when(cacheManager).getRuntimeConfiguration();
    doReturn(configMap).when(configuration).getCacheConfigurations();
    doReturn(null).when(configMap).get(eq("missingAlias"));

    assertThrows(EntityNotFoundException.class,
            () -> cacheProvider.getCacheDefinition(cacheManagerName, "missingAlias"));
  }

  @Test
  void parseKey_throwsForInvalidIntegerKey() {
    assertThrows(InvalidInputException.class, () -> cacheProvider.parseKey("abc", Integer.class));
  }

  @Test
  void parseValue_throwsForWrongBooleanType() {
    assertThrows(InvalidInputException.class, () -> cacheProvider.parseValue("true", Boolean.class));
  }

  @Test
  void parseValue_supportsBigIntegerAndBigDecimal() {
    BigInteger bi = cacheProvider.parseValue("12345678901234567890", BigInteger.class);
    BigDecimal bd = cacheProvider.parseValue("12345.6789", BigDecimal.class);

    assertEquals(new BigInteger("12345678901234567890"), bi);
    assertEquals(new BigDecimal("12345.6789"), bd);
  }
}