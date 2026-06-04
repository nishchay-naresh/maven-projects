package com.nish.api.controller;

import com.nish.api.service.CacheService;
import com.nish.api.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller class for managing cache entries.
 * <p>
 * Provides endpoints for fetching, updating, and deleting entries in a specified cache.
 *
 */
@RestController
@RequestMapping(value = "/cache-managers/{cmId}/caches/{id}/entries", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheEntriesController {

  private final CacheService cacheService;

  /**
   * Initializes a new instance of {@code CacheEntriesController} with the specified {@code CacheService}.
   * This service is responsible for managing operations on cache entries.
   *
   * @param cacheService the service used to handle caching functionality
   */
  public CacheEntriesController(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  /**
   * Retrieves the specified entries from the cache.
   *
   * @param <K>              the type of keys used in the cache
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the cache manager
   * @param cacheAlias       the alias of the cache
   * @param params           a {@link MultiValueMap} containing request parameters, primarily used to extract the "k" query parameter values
   * @param apiVersion       the API version header, defaults to {@link Constants#FIRST_API_VERSION} if not provided
   * @return a {@link ResponseEntity} containing the retrieved cache entries
   */
  @GetMapping
  public <K, V> ResponseEntity<Object> getEntries(@PathVariable("cmId") String cacheManagerName,
                                                  @PathVariable("id") String cacheAlias,
                                                  @RequestParam MultiValueMap<String, String> params,
                                                  @RequestHeader(value = "X-API-Version", defaultValue = Constants.FIRST_API_VERSION) String apiVersion) {
    List<String> list = params.getOrDefault("k", Collections.emptyList());
    Set<String> keys = new HashSet<>(list);
    Map<K, V> cacheEntries = cacheService.getEntries(cacheManagerName, cacheAlias, keys);
    return ResponseEntity.status(HttpStatus.OK)
        .body(cacheEntries);
  }

  /**
   * Updates or adds the specified entries in the cache.
   *
   * @param <V>              the type of values stored in the cache
   * @param cacheManagerName the name of the cache manager
   * @param cacheAlias       the alias of the cache
   * @param cacheEntries     a map of keys and values to update or add
   * @param apiVersion       the API version header, defaults to {@link Constants#FIRST_API_VERSION} if not provided
   * @return a {@link ResponseEntity} with no content
   */
  @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public <V> ResponseEntity<Void> updateEntries(@PathVariable("cmId") String cacheManagerName,
                                                @PathVariable("id") String cacheAlias,
                                                @RequestBody Map<String, V> cacheEntries,
                                                @RequestHeader(value = "X-API-Version", defaultValue = Constants.FIRST_API_VERSION) String apiVersion) {
    cacheService.updateEntries(cacheManagerName, cacheAlias, cacheEntries);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Deletes the specified entries from the cache.
   *
   * @param cacheManagerName the name of the cache manager
   * @param cacheAlias       the alias of the cache
   * @param keys             the set of keys to delete
   * @param apiVersion       the API version header, defaults to {@link Constants#FIRST_API_VERSION} if not provided
   * @return a {@link ResponseEntity} with no content
   */
  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> deleteEntries(@PathVariable("cmId") String cacheManagerName,
                                            @PathVariable("id") String cacheAlias,
                                            @RequestBody Set<String> keys,
                                            @RequestHeader(value = "X-API-Version", defaultValue = Constants.FIRST_API_VERSION) String apiVersion) {
    cacheService.deleteEntries(cacheManagerName, cacheAlias, keys);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Deletes a specific entry from the cache by its key.
   *
   * @param cacheManagerName the name of the cache manager
   * @param cacheAlias       the alias of the cache
   * @param key              the key of the entry to delete
   * @param apiVersion       the API version header, defaults to {@link Constants#FIRST_API_VERSION} if not provided
   * @return a {@link ResponseEntity} with no content
   */
  @DeleteMapping("/{key}")
  public ResponseEntity<Void> deleteEntry(@PathVariable("cmId") String cacheManagerName,
                                          @PathVariable("id") String cacheAlias,
                                          @PathVariable("key") String key,
                                          @RequestHeader(value = "X-API-Version", defaultValue = Constants.FIRST_API_VERSION) String apiVersion) {
    cacheService.deleteEntry(cacheManagerName, cacheAlias, key);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
