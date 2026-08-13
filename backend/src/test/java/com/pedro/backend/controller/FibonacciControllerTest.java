package com.pedro.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pedro.backend.exception.GlobalExceptionHandler;
import com.pedro.backend.service.FibonacciService;

class FibonacciControllerTest {

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        FibonacciService service = new FibonacciService();
        FibonacciController controller = new FibonacciController(service);
        mvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void fibonacciZero() throws Exception {
        mvc.perform(get("/api/fibonacci/0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.n").value(0))
            .andExpect(jsonPath("$.value").value("0"));
    }

    @Test
    void fibonacciTen() throws Exception {
        mvc.perform(get("/api/fibonacci/10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.n").value(10))
            .andExpect(jsonPath("$.value").value("55"));
    }

    @Test
    void fibonacciHundred() throws Exception {
        mvc.perform(get("/api/fibonacci/100"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.n").value(100))
            .andExpect(jsonPath("$.value").value("354224848179261915075"));
    }

    @Test
    void cacheBehaviorAcrossRequests() throws Exception {
        // First request - should not be cached
        mvc.perform(get("/api/fibonacci/100"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.n").value(100))
            .andExpect(jsonPath("$.cached").value(false));

        // Second request - should be cached
        mvc.perform(get("/api/fibonacci/100"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.n").value(100))
            .andExpect(jsonPath("$.cached").value(true));
    }

    @Test
    void negativeReturnsBadRequest() throws Exception {
        mvc.perform(get("/api/fibonacci/-1"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("N must be a positive integer not exceeding 10000."));
    }
}
