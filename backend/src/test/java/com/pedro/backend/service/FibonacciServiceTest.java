package com.pedro.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

class FibonacciServiceTest {

    private final FibonacciService service = new FibonacciService();

    @Test
    void shouldReturnZeroForZero() {
        assertEquals(BigInteger.ZERO, service.calcular(0));
    }

    @Test
    void shouldReturnOneForOne() {
        assertEquals(BigInteger.ONE, service.calcular(1));
    }

    @Test
    void shouldReturnOneForTwo() {
        assertEquals(BigInteger.ONE, service.calcular(2));
    }

    @Test
    void shouldReturnFiveForFive() {
        assertEquals(BigInteger.valueOf(5), service.calcular(5));
    }

    @Test
    void shouldReturnFiftyFiveForTen() {
        assertEquals(BigInteger.valueOf(55), service.calcular(10));
    }

    @Test
    void shouldReturnSixThousandSevenHundredAndSixtyFiveForTwenty() {
        assertEquals(BigInteger.valueOf(6765), service.calcular(20));
    }

    @Test
    void shouldReturnLargeValueForOneHundred() {
        assertEquals(new BigInteger("354224848179261915075"), service.calcular(100));
    }

    @Test
    void shouldThrowForNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> service.calcular(-1));
    }
}
