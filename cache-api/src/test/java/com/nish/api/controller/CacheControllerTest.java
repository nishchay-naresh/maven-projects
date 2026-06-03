/*
 * Copyright (c) 2024 Software AG, Darmstadt, Germany and/or Software AG USA Inc., Reston, VA, USA, and/or its subsidiaries and/or its affiliates and/or their licensors.
 * Use, reproduction, transfer, publication or disclosure is prohibited except as specifically provided for in your License Agreement with Software AG.
 */
package com.nish.api.controller;

import com.nish.api.model.CacheDefinition;
import com.nish.api.service.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CacheControllerTest {

  private static final String CACHE_MANAGER = "mockCacheManager";
  private static final String CACHE_ALIAS = "mockCache";

  @Mock
  private CacheService cacheService;

  @InjectMocks
  private CacheController cacheController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(cacheController).build();
  }

  @Test
  void test_getCacheDefinition() throws Exception {
    CacheDefinition definition = new CacheDefinition("java.lang.String", "java.lang.Integer");
    when(cacheService.getCacheDefinition(CACHE_MANAGER, CACHE_ALIAS)).thenReturn(definition);

    mockMvc.perform(get("/cache-managers/{cmId}/caches/{id}", CACHE_MANAGER, CACHE_ALIAS))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().json("{\"keyType\":\"java.lang.String\",\"valueType\":\"java.lang.Integer\"}"));
  }
}