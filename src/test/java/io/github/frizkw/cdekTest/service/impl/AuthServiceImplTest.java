package io.github.frizkw.cdekTest.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import io.github.frizkw.cdekTest.dto.request.LoginRequest;
import io.github.frizkw.cdekTest.dto.response.AuthResponse;
import io.github.frizkw.cdekTest.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldAuthenticateAndReturnToken() {
        LoginRequest request = new LoginRequest(
                "user",
                "password"
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getName()).thenReturn("user");
        when(jwtTokenProvider.createToken("user")).thenReturn("jwt-token");

        AuthResponse response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.token());
        assertEquals("user", response.username());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).createToken("user");
    }

    @Test
    void shouldCallAuthenticationManagerWithCorrectCredentials() {
        LoginRequest request = new LoginRequest(
                "testUser",
                "testPass"
        );

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(jwtTokenProvider.createToken(anyString())).thenReturn("token");

        authService.authenticate(request);

        verify(authenticationManager).authenticate(
                argThat(auth ->
                        auth.getPrincipal().equals("testUser") &&
                                auth.getCredentials().equals("testPass")
                )
        );
    }
}