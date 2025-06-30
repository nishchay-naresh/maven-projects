package com.nishchay.sample;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MathUtilsTest {

    private MathUtils mathUtils;

    @Before
    public void setup() {
        mathUtils = new MathUtils();
    }

    @After
    public void cleanup() {
        mathUtils = new MathUtils();
    }

    @Test
    public void testSum() {
        Assert.assertEquals("Math.sum() is not working fine", 5, mathUtils.sum(2, 3));
        Assert.assertEquals("Fail", -5, mathUtils.sum(-2, -3));
    }

    @Test
    public void testSubtract() {
        Assert.assertEquals("Fail", -2, mathUtils.subtract(3, 5));
        Assert.assertEquals("Fail", 5, mathUtils.subtract(8, 3));
    }

    @Test
    public void testMultiply() {
        Assert.assertEquals("Fail", -12, mathUtils.multiply(3, -4));
        Assert.assertEquals("Fail", 9, mathUtils.multiply(-3, -3));
    }

    @Test
    public void testDivide() {
        Assert.assertEquals("Fail", 3, mathUtils.divide(13, 4));
        Assert.assertEquals("Fail", 1, mathUtils.divide(6, 6));
    }


}