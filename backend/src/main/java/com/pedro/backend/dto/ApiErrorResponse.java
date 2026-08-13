package com.pedro.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiErrorResponse", description = "Standard error response")
public record ApiErrorResponse(
    @Schema(description = "HTTP status code", example = "400") int status,
    @Schema(description = "Error reason", example = "Bad Request") String error,
    @Schema(description = "Error message returned by the server") String message
) {}
