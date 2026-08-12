package com.pedro.backend.service;

import java.math.BigInteger;

public class FibonacciService {

    public BigInteger calcular(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N must be positive or zero.");
        }

        if (n == 0) {
            return BigInteger.ZERO;
        }
        if (n == 1) {
            return BigInteger.ONE;
        }

        BigInteger anterior = BigInteger.ZERO;
        BigInteger atual = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            BigInteger seguinte = anterior.add(atual);
            anterior = atual;
            atual = seguinte;
        }

        return atual;
    }
}
