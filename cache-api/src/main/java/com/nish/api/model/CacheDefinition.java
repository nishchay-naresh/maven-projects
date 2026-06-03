

package com.nish.api.model;

/**
 * Represents the definition of a cache, including the types of its keys and values.
 * This record provides an immutable structure for storing the key and value types
 * associated with a cache configuration.
 *
 * @param keyType   the type of the cache key
 * @param valueType the type of the cache value
 */
public record CacheDefinition(String keyType, String valueType) {
}
