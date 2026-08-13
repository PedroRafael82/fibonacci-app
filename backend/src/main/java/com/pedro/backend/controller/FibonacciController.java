package com.pedro.backend.controller;

import java.math.BigInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.backend.dto.FibonacciResponse;
import com.pedro.backend.dto.ApiErrorResponse;
import com.pedro.backend.service.FibonacciService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/fibonacci")
public class FibonacciController {

	private final FibonacciService service;

	public FibonacciController(FibonacciService service) {
		this.service = service;
	}

	@Operation(summary = "Calculate a Fibonacci number", description = "Returns the Fibonacci value for a given non-negative index. Previously calculated values may be returned from the in-memory cache.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successful operation",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FibonacciResponse.class))),
		@ApiResponse(responseCode = "400", description = "Bad Request",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
	@GetMapping("/{n}")
	public ResponseEntity<FibonacciResponse> get(
			@Parameter(in = ParameterIn.PATH, description = "Fibonacci index (integer, min 0)", example = "10", required = true)
			@PathVariable int n) {
		boolean cachedBefore = service.isCached(n);
		BigInteger value = service.calcular(n);
		FibonacciResponse resp = new FibonacciResponse(n, value.toString(), cachedBefore);
		return ResponseEntity.ok(resp);
	}
}
