package com.nish.api.components;

import com.nish.api.exception.InvalidInputException;
import com.nish.api.exception.CacheApiException;
import com.nish.api.utils.AppStatusCode;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.multi.XmlMultiConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CacheConfigLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfigLoader.class);
  private final String cacheDataType;
  private final String externalDir;
  private final boolean whitelistEnabled;
  private final String cacheManagerWhitelist;

  public CacheConfigLoader(String externalDir, boolean whitelistEnabled, String cacheManagerWhitelist, String cacheDataType) {
    this.externalDir = externalDir;
    this.cacheDataType = cacheDataType;
    this.whitelistEnabled = whitelistEnabled;
    this.cacheManagerWhitelist = cacheManagerWhitelist;
  }

  public Map<String, CacheManager> loadCacheManagerMap() {
    return getConfigFiles().stream().collect(HashMap::new, (m, e) -> m.putAll(populateCacheManagers(e)), HashMap::putAll);
  }

  List<Path> getConfigFiles() {
    if (externalDir.isBlank()) {
      return Collections.emptyList();
    }

    Path root;
    try {
      root = Paths.get(externalDir).toRealPath();
    } catch (IOException e) {
      throw new CacheApiException("Invalid directory: " + externalDir, e);
    }

    LOGGER.info("Finding Ehcache XML configurations in: {}", root);

    if (!Files.exists(root) || !Files.isDirectory(root)) {
      throw new CacheApiException("Invalid directory: " + externalDir);
    }

    try (Stream<Path> stream = Files.list(root)) {
      return stream.filter(Files::isRegularFile).filter(p -> p.getFileName().toString().endsWith(".xml")).collect(Collectors.toList());
    } catch (IOException e) {
      throw new CacheApiException("Unable to read directory: " + externalDir, e);
    }
  }

  Map<String, CacheManager> populateCacheManagers(Path xmlPath) {
    LOGGER.info("Loading caches from: {}", xmlPath);

    Map<String, CacheManager> map = new HashMap<>();
    XmlMultiConfiguration multipleConfiguration;

    try {
      multipleConfiguration = XmlMultiConfiguration.from(xmlPath.toUri().toURL()).build();
    } catch (MalformedURLException e) {
      throw new CacheApiException("Invalid path: " + xmlPath, e);
    }

    validateCacheType(multipleConfiguration);

    for (String cacheManagerName : multipleConfiguration.identities()) {
      if (isWhitelisted(cacheManagerName)) {
        Configuration configuration = multipleConfiguration.configuration(cacheManagerName);
        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(configuration);
        cacheManager.init();
        map.put(cacheManagerName, cacheManager);
      }
    }
    return map;
  }


  private void validateCacheType(XmlMultiConfiguration multipleConfiguration) {
    multipleConfiguration.identities().forEach(cacheManagerName -> multipleConfiguration.configuration(cacheManagerName).getCacheConfigurations().forEach((cacheName, cacheConfig) -> {
      if (isInvalidDataType(cacheConfig.getKeyType().getName())) {
        LOGGER.error("Invalid keyType - {} for cache-manager - {}, cache - {}", cacheConfig.getKeyType().getName(), cacheManagerName, cacheName);
        throw new InvalidInputException(AppStatusCode.INVALID_KEY);
      }
      if (isInvalidDataType(cacheConfig.getValueType().getName())) {
        LOGGER.error("Invalid valueType - {} for cache-manager - {}, cache - {}", cacheConfig.getValueType().getName(), cacheManagerName, cacheName);
        throw new InvalidInputException(AppStatusCode.INVALID_VALUE);
      }
    }));
  }

  private boolean isInvalidDataType(String dataType) {
    return !cacheDataType.contains(dataType);
  }

  private boolean isWhitelisted(String cacheManagerName) {
    if (whitelistEnabled) {
      return cacheManagerWhitelist.contains(cacheManagerName);
    } else {
      // if whitelistEnabled==false means everything is visible (no restriction)
      return true;
    }
  }
}
