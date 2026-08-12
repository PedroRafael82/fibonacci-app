package com.pedro.backend.dto;

public record ApiErrorResponse(
    int status,
    String error,
    String message
) {}
