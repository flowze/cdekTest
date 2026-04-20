package io.github.frizkw.cdekTest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank(message = "Username не может быть пустым")
        @Size(min = 3, max = 50, message = "Username должен быть от 3 до 50 символов")
        String username,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 6, message = "Пароль должен быть минимум 6 символов")
        String password
) {}