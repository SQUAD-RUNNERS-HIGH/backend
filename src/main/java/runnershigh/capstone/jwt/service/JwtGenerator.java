package runnershigh.capstone.jwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import runnershigh.capstone.jwt.config.JwtProperties;

@Component
public class JwtGenerator {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public JwtGenerator(JwtProperties jwtProperties) {
        this.accessSecretKey = Keys.hmacShaKeyFor(jwtProperties.getAccessSecretKey().getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(jwtProperties.getRefreshSecretKey().getBytes());
        this.accessExpirationTime = jwtProperties.getAccessExpirationTime();
        this.refreshExpirationTime = jwtProperties.getRefreshExpirationTime();
    }

    public String generateAccessToken(Long userId) {
        return Jwts.builder()
            .subject(String.valueOf(userId))
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + (accessExpirationTime * 1000)))
            .signWith(accessSecretKey)
            .compact();
    }

    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
            .subject(String.valueOf(userId))
            .issuedAt(new Date())
            .expiration(new Date(
                System.currentTimeMillis() + (refreshExpirationTime * 1000)))
            .signWith(refreshSecretKey)
            .compact();
    }

}
