package io.github.frizkw.cdekTest.dto.request;

public record LoginRequest(
        String username,
        String password
) {}