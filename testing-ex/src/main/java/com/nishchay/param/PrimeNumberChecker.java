package com.nishchay.param;

import java.util.Collections;
import java.util.Map;
import java.util.stream.IntStream;

public class PrimeNumberChecker<K,V> {

    Map<String,Long> map;

    public PrimeNumberChecker(){
        map = Collections.emptyMap();
    }

    public PrimeNumberChecker(Map<String, Long> map) {
        this.map = map;
    }

    // Declarative way to find the Prime.
    public boolean isPrime(int number) {

        if (number <= 1) {
            return false;
        }

        for (int i = 2; i <= number / 2; i++)
            if (number % i == 0)
                return false;
        return true;
    }

    public void printMap(Map<String,Long> map) {
        map.forEach((key, value) -> System.out.println(key + "-> " + value));
    }
}
