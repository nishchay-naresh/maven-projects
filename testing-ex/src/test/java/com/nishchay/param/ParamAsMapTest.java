package com.nishchay.param;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParamAsMapTest {
   private Map<String,Long> inputMap;
   private PrimeNumberChecker primeNumberChecker;

   @Before
   public void initialize() {
      primeNumberChecker = new PrimeNumberChecker(inputMap);
   }

   // Each parameter should be placed as an argument here
   // Every time runner triggers, it will pass the arguments
   // from parameters we defined in primeNumbers() method

   public ParamAsMapTest(Map<String,Long> inputMap) {
      this.inputMap = inputMap;
   }

   @Parameters
   public static List<Map<String,Long>> numbers() {

      Map<String,Long> map1 = new HashMap<>();
      map1.put("kay1", 101L);
      map1.put("kay2", 102L);
      map1.put("kay3", 103L);

      return Arrays.asList(
              map1,
              Collections.emptyMap(),
              null
      );

   }

   @Test
   public void testMapPrinting() {
      System.out.println("Parameterized map is : " + inputMap);
      if(null == inputMap){
         System.out.println("map is null, so not printing it");
      }else {
         primeNumberChecker.printMap(inputMap);
      }

   }
}