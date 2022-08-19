package com.nishchay.param;

import com.nishchay.sample.MathUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;

// Parameterized via Field Injection
@RunWith(value = Parameterized.class)
public class ParameterizedTestFieldInjection {

    //default value = 0
    @Parameter(value = 0)
    public int numberA;

    @Parameter(value = 1)
    public int numberB;

    @Parameter(value = 2)
    public int expected;

    @Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 1, 2},
                {2, 2, 4},
                {8, 2, 10},
                {4, 5, 9},
                {5, 5, 10}
        });
    }

    @Test
    public void test_addTwoNumbers() {
        MatcherAssert.assertThat(MathUtils.add(numberA, numberB), is(expected));
    }

}