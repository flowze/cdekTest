package io.github.frizkw.cdekTest.controller;

import io.github.frizkw.cdekTest.dto.request.LoginRequest;
import io.github.frizkw.cdekTest.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return jwtTokenProvider.createToken(request.username());
    }
}