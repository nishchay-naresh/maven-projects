/*
 * Copyright (c) 2024 Software AG, Darmstadt, Germany and/or Software AG USA Inc., Reston, VA, USA, and/or its subsidiaries and/or its affiliates and/or their licensors.
 * Use, reproduction, transfer, publication or disclosure is prohibited except as specifically provided for in your License Agreement with Software AG.
 */
package com.nish.api.controller;

import com.nish.api.service.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CacheControllerTest {

  private final String CACHE_KEY = "mockKey";
  private final String CACHE_VALUE = "mockValue";
  private final String CACHE_MANAGER = "mockCacheManager";
  private final String CACHE_ALIAS = "mockCache";
  @Mock
  private CacheService cacheService;
  @InjectMocks
  private CacheController cacheController;
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    setupMock();
    mockMvc = MockMvcBuilders.standaloneSetup(cacheController).build();
  }

  private void setupMock() {
    Mockito.when(cacheService.getEntries(CACHE_MANAGER, CACHE_ALIAS, CACHE_KEY)).thenReturn(CACHE_VALUE);
    Mockito.when(cacheService.updateEntries(CACHE_MANAGER, CACHE_ALIAS, CACHE_KEY, CACHE_VALUE)).thenReturn(true);
    Mockito.when(cacheService.deleteEntries(CACHE_MANAGER, CACHE_ALIAS, CACHE_KEY)).thenReturn(CACHE_VALUE);
  }

  @Test
  void test_crud_Entry() throws Exception {

    mockMvc.perform(get("/cache/" + CACHE_MANAGER + "/" + CACHE_ALIAS + "/" + CACHE_KEY))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string(CACHE_VALUE));

    mockMvc.perform(post("/cache/" + CACHE_MANAGER + "/" + CACHE_ALIAS + "/" + CACHE_KEY + "/" + CACHE_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));

    mockMvc.perform(delete("/cache/" + CACHE_MANAGER + "/" + CACHE_ALIAS + "/" + CACHE_KEY))
            .andExpect(status().isOk())
            .andExpect(content().string(CACHE_VALUE));

  }
}