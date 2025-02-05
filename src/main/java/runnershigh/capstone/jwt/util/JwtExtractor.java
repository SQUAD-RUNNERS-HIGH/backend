package runnershigh.capstone.jwt.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import runnershigh.capstone.jwt.config.JwtProperties;
import runnershigh.capstone.jwt.enums.AuthConstants;

@Component
public class JwtExtractor {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    public JwtExtractor(JwtProperties jwtProperties) {
        this.accessSecretKey = Keys.hmacShaKeyFor(jwtProperties.getAccessSecretKey().getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(jwtProperties.getRefreshSecretKey().getBytes());
    }

    public String extractAccessToken(HttpServletRequest request) {
        String token = request.getHeader(AuthConstants.AUTHORIZATION_HEADER.getValue());
        if (token != null && token.startsWith(AuthConstants.BEARER_PREFIX.getValue())) {
            return token.substring(
                AuthConstants.BEARER_PREFIX.getValue().length());  // "Bearer " 부분을 잘라내고 실제 토큰만 반환
        }
        return null;
    }

    public String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AuthConstants.REFRESH_COOKIE_NAME.getValue().equals(cookie.getName())) {
                    return cookie.getValue();  // 쿠키에서 리프레시 토큰 반환
                }
            }
        }
        return null;
    }

    public String extractUserIdByAccessToken(String token) {
        return Jwts.parser()
            .setSigningKey(accessSecretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }


    public String extractUserIdByRefreshToken(String token) {
        return Jwts.parser()
            .setSigningKey(refreshSecretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }


}
