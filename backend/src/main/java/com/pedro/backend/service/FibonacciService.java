package com.pedro.backend.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FibonacciService {

    private final List<BigInteger> cache = new ArrayList<>(Arrays.asList(BigInteger.ZERO, BigInteger.ONE));

    public BigInteger calcular(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N must be positive or zero.");
        }

        if (n < cache.size()) {
            return cache.get(n);
        }

        for (int i = cache.size(); i <= n; i++) {
            BigInteger anterior = cache.get(i - 2);
            BigInteger atual = cache.get(i - 1);
            cache.add(anterior.add(atual));
        }

        return cache.get(n);
    }

    int getCacheSize() {
        return cache.size();
    }

    BigInteger getCacheValue(int index) {
        return cache.get(index);
    }
}
