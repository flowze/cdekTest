package io.github.frizkw.cdekTest.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretString;

    private SecretKey key;

    @PostConstruct
    public void init() {
        // Декодируем строку из Base64 и создаем ключ для HMAC-SHA
        byte[] keyBytes = Decoders.BASE64.decode(secretString);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String username) {
        return Jwts.builder()
                .subject(username) // Лаконичные названия методов без "set"
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key) // Здесь синтаксис почти не изменился
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // Теперь используем Jwts.parser() напрямую, без Builder
            Jwts.parser()
                    .verifyWith(key) // Вместо setSigningKey
                    .build()
                    .parseSignedClaims(token); // Вместо parseClaimsJws
            return true;
        } catch (Exception e) {
            // Здесь можно логировать конкретные ошибки (просрочен, неверная подпись и т.д.)
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload() // Вместо getBody()
                .getSubject();
    }
}
