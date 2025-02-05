package runnershigh.capstone.jwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.jwt.config.JwtProperties;
import runnershigh.capstone.jwt.dto.LoginRequest;
import runnershigh.capstone.jwt.dto.LoginResponse;
import runnershigh.capstone.jwt.enums.AuthConstants;
import runnershigh.capstone.jwt.service.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class JwtController {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    private static final String COOKIE_HEADER = "Set-Cookie";

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest,
        HttpServletResponse response) {
        LoginResponse loginResponse = jwtService.login(loginRequest.loginId(),
            loginRequest.password());

        response.setHeader(AuthConstants.AUTHORIZATION_HEADER.getValue(),
            AuthConstants.BEARER_PREFIX.getValue() + loginResponse.accessToken());

        setRefreshTokenInCookie(response, loginResponse.refreshToken(),
            jwtProperties.getRefreshExpirationTime());

        return loginResponse;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserFromToken(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId != null) {
            return ResponseEntity.ok("Authenticated userId : " + userId);
        } else {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String userId = (String) request.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        jwtService.logout(userId);

        ResponseCookie expiredCookie = ResponseCookie.from(
                AuthConstants.REFRESH_COOKIE_NAME.getValue(), "")
            .maxAge(0)  // 쿠키 삭제
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build();

        response.setHeader(COOKIE_HEADER, expiredCookie.toString());

        return ResponseEntity.ok("Logged out successfully");
    }

    private void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken,
        Long refreshExpirationTime) {
        ResponseCookie cookie = ResponseCookie.from(AuthConstants.REFRESH_COOKIE_NAME.getValue(),
                refreshToken)
            .maxAge(refreshExpirationTime)
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build();
        response.setHeader(COOKIE_HEADER, cookie.toString());
    }
}
