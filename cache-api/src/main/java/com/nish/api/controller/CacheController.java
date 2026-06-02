
package com.nish.api.controller;

import com.nish.api.model.CacheDefinition;
import com.nish.api.service.CacheService;
import com.nish.api.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing cache definitions.
 * <p>
 * This controller provides endpoints to retrieve metadata about caches
 * managed by specific cache managers. It handles incoming HTTP requests
 * related to caches and delegates business logic to the {@link CacheService}.
 * </p>
 *
 */
@RestController
@RequestMapping(value = "/cache-managers/{cmId}/caches/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheController {

  private final CacheService cacheService;
  /**
   * Initializes a new instance of {@code CacheController} with the specified {@code CacheService}.
   * This service is responsible for managing cache operations.
   *
   * @param cacheService the service used to handle caching functionality
   */
  public CacheController(CacheService cacheService){
    this.cacheService = cacheService;
  }

  /**
   * Retrieves the definition of a cache specified by the cache manager ID and cache alias.
   * <p>
   * This endpoint validates the input parameters, retrieves the cache definition from the service layer,
   * and returns the metadata as a JSON response.
   * </p>
   *
   * @param cacheManagerId the unique identifier of the cache manager
   * @param cacheAlias     the alias of the cache whose definition is being retrieved
   * @param apiVersion     the API version header, defaults to {@link Constants#FIRST_API_VERSION} if not provided
   * @return a {@link ResponseEntity} containing the {@link CacheDefinition} of the specified cache
   */

  @GetMapping
  public ResponseEntity<CacheDefinition> getCacheDefinition(@PathVariable("cmId") String cacheManagerId,
                                                            @PathVariable("id") String cacheAlias,
                                                            @RequestHeader(value = "X-API-Version", defaultValue = Constants.FIRST_API_VERSION) String apiVersion) {
    CacheDefinition cacheDefinition = cacheService.getCacheDefinition(cacheManagerId, cacheAlias);
    return ResponseEntity.status(HttpStatus.OK)
        .body(cacheDefinition);
  }

}