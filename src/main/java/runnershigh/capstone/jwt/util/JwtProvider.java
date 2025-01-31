package runnershigh.capstone.jwt.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import runnershigh.capstone.jwt.config.JwtProperties;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public JwtProvider(JwtProperties jwtProperties) {
        this.accessSecretKey = Keys.hmacShaKeyFor(jwtProperties.getAccessSecretKey().getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(jwtProperties.getRefreshSecretKey().getBytes());
        this.accessExpirationTime = jwtProperties.getAccessExpirationTime();
        this.refreshExpirationTime = jwtProperties.getRefreshExpirationTime();
    }

    public String generateAccessToken(String loginId) {
        return Jwts.builder()
            .subject(loginId)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + (accessExpirationTime * 1000)))
            .signWith(accessSecretKey)
            .compact();
    }

    public String generateRefreshToken(String loginId) {
        return Jwts.builder()
            .subject(loginId)
            .issuedAt(new Date())
            .expiration(new Date(
                System.currentTimeMillis() + (refreshExpirationTime * 1000)))
            .signWith(refreshSecretKey)
            .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parser()
                .setSigningKey(accessSecretKey)
                .build()
                .parseClaimsJws(accessToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parser()
                .setSigningKey(refreshSecretKey)
                .build()
                .parseClaimsJws(refreshToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractLoginIdByAccessToken(String token) {
        return Jwts.parser()
            .setSigningKey(accessSecretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public String extractLoginIdByRefreshToken(String token) {
        return Jwts.parser()
            .setSigningKey(refreshSecretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}
