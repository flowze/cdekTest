package io.github.frizkw.cdekTest.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password; // Хэш пароля (BCrypt)
    private Role role;       // Роль (USER, ADMIN)
}
