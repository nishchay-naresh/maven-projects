package com.nishchay.param;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
// 1
@RunWith(Parameterized.class)
public class OddCheckerTest {

   @Parameterized.Parameter(0)
   public Integer inputNumber;

   @Parameterized.Parameters(name="Test - {0} is odd number: {1}")
   public static List<Integer> numbers() {
      return Arrays.asList( 1 , 6 , 19 , 22 );
   }
   // 5
   @Test
   public void testOddNumberChecker() {
      System.out.println("Parameterized Number is: " + inputNumber);
      System.out.println(inputNumber % 2 == 1);
   }

}