package io.github.frizkw.cdekTest.service.impl;

import io.github.frizkw.cdekTest.dto.request.LoginRequest;
import io.github.frizkw.cdekTest.dto.request.RegisterRequest;
import io.github.frizkw.cdekTest.dto.response.AuthResponse;
import io.github.frizkw.cdekTest.mapper.UserMapper;
import io.github.frizkw.cdekTest.model.Role;
import io.github.frizkw.cdekTest.model.User;
import io.github.frizkw.cdekTest.security.JwtTokenProvider;
import io.github.frizkw.cdekTest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String token = jwtTokenProvider.createToken(authentication.getName());
        return new AuthResponse(token, request.username());
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userMapper.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с именем '" + request.username() + "' уже существует");
        }


        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userMapper.insert(user);

        String token = jwtTokenProvider.createToken(user.getUsername());

        return new AuthResponse(token, user.getUsername());
    }
}
