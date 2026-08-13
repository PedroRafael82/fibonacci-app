package com.pedro.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FibonacciResponse", description = "Response containing the requested index, the Fibonacci value as string, and a cached flag")
public record FibonacciResponse(
    @Schema(description = "Requested Fibonacci index", example = "10") int n,
    @Schema(description = "Fibonacci value represented as string") String value,
    @Schema(description = "True if the result was available in the in-memory cache before this request") boolean cached
) {}
