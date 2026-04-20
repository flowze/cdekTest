package io.github.frizkw.cdekTest.dto.response;

public record AuthResponse(
        String token,
        String username
) {}