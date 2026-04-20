package io.github.frizkw.cdekTest.service;

import io.github.frizkw.cdekTest.dto.request.LoginRequest;
import io.github.frizkw.cdekTest.dto.request.RegisterRequest;
import io.github.frizkw.cdekTest.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}
