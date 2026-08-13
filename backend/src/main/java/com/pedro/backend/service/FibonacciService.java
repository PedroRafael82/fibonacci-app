package com.pedro.backend.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FibonacciService {

    private final List<BigInteger> cache = new ArrayList<>(Arrays.asList(BigInteger.ZERO, BigInteger.ONE));

    public BigInteger calcular(int n) {
        if (n < 0 || n > 10000) {
            throw new IllegalArgumentException("N must be a positive integer not exceeding 10000.");
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

    public boolean isCached(int n) {
        return n >= 0 && n < cache.size();
    }

    int getCacheSize() {
        return cache.size();
    }

    BigInteger getCacheValue(int index) {
        return cache.get(index);
    }
}
