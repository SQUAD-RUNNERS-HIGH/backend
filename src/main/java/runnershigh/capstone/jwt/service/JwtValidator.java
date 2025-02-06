package runnershigh.capstone.jwt.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import runnershigh.capstone.jwt.config.JwtProperties;

@Component
public class JwtValidator {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    public JwtValidator(JwtProperties jwtProperties) {
        this.accessSecretKey = Keys.hmacShaKeyFor(jwtProperties.getAccessSecretKey().getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(jwtProperties.getRefreshSecretKey().getBytes());
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
}
