package com.pedro.backend.controller;

import java.math.BigInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.backend.dto.FibonacciResponse;
import com.pedro.backend.service.FibonacciService;

@RestController
@RequestMapping("/api/fibonacci")
public class FibonacciController {

	private final FibonacciService service;

	public FibonacciController(FibonacciService service) {
		this.service = service;
	}

	@GetMapping("/{n}")
	public ResponseEntity<FibonacciResponse> get(@PathVariable int n) {
		boolean cachedBefore = service.isCached(n);
		BigInteger value = service.calcular(n);
		FibonacciResponse resp = new FibonacciResponse(n, value.toString(), cachedBefore);
		return ResponseEntity.ok(resp);
	}
}
