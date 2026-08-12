package com.pedro.backend.dto;

public record FibonacciResponse(
    int n,
    String value,
    boolean cached
) {}
