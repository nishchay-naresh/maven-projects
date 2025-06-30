package com.nishchay.param;


import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
@RunWith(Parameterized.class)
public class FibonacciTest {

    @Parameterized.Parameter(0)
    public Integer inputNumber;

    @Parameterized.Parameter(1)
    public Boolean expectedResult;

    // 4
    @Parameterized.Parameters(name="Test - {0} is odd number: {1}")
    public static Collection numbers() {
        return Arrays.asList(new Object[][] {
                { 1, true },
                { 6, false },
                { 19, true },
                { 22, false },
                { 23, true }
        });
    }
    // 5
    @Test
    public void testOddNumberChecker() {
        System.out.println("Parameterized Number is: " + inputNumber);
        assertEquals(expectedResult, inputNumber % 2 == 1);
    }
}