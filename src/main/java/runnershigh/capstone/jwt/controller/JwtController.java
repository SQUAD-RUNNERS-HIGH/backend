package runnershigh.capstone.jwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.jwt.config.JwtProperties;
import runnershigh.capstone.jwt.dto.LoginRequest;
import runnershigh.capstone.jwt.dto.TokenResponse;
import runnershigh.capstone.jwt.enums.AuthConstants;
import runnershigh.capstone.jwt.service.JwtService;
import runnershigh.capstone.jwt.util.CookieUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class JwtController {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final CookieUtil cookieUtil;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest,
        HttpServletResponse response) {

        TokenResponse tokenResponse = jwtService.login(loginRequest.loginId(),
            loginRequest.password());

        log.info(tokenResponse.toString());

        setHeaderAndRefreshTokenCookie(response, tokenResponse);

        log.info(tokenResponse.toString());

        return tokenResponse;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserFromToken(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        if (Objects.isNull(userId)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
        return ResponseEntity.ok("Authenticated userId : " + userId);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String userId = (String) request.getAttribute("userId");

        if (Objects.isNull(userId)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        jwtService.logout(userId);

        cookieUtil.clearRefreshTokenCookie(response);

        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(HttpServletRequest request, HttpServletResponse response) {
        String userId = (String) request.getAttribute("userId");

        TokenResponse tokenResponse = jwtService.refresh(userId);

        setHeaderAndRefreshTokenCookie(response, tokenResponse);

        return tokenResponse;
    }

    private void setHeaderAndRefreshTokenCookie(HttpServletResponse response,
        TokenResponse tokenResponse) {
        response.setHeader(AuthConstants.AUTHORIZATION_HEADER.getValue(),
            AuthConstants.BEARER_PREFIX.getValue() + tokenResponse.accessToken());

        cookieUtil.setRefreshTokenCookie(response, tokenResponse.refreshToken(),
            jwtProperties.getRefreshExpirationTime());
    }


}
