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
import runnershigh.capstone.jwt.dto.JwtRequest;
import runnershigh.capstone.jwt.dto.JwtResponse;
import runnershigh.capstone.jwt.service.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class JwtController {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    private static final String REFRESH_COOKIE_NAME = "refresh_token";

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) {
        JwtResponse jwtResponse = jwtService.login(jwtRequest.loginId(), jwtRequest.password());

        response.setHeader("Authorization", "Bearer" + jwtResponse.accessToken());

        setRefreshTokenInCookie(response, jwtResponse.refreshToken(),
            jwtProperties.getRefreshExpirationTime());

        return jwtResponse;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserFromToken(HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginId");
        if (loginId != null) {
            return ResponseEntity.ok("Authenticated user: " + loginId);
        } else {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String loginId = (String) request.getAttribute("loginId");

        if (loginId == null) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        jwtService.logout(loginId);

        ResponseCookie expiredCookie = ResponseCookie.from(REFRESH_COOKIE_NAME, "")
            .maxAge(0)  // 쿠키 삭제
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build();

        response.setHeader("Set-Cookie", expiredCookie.toString());

        return ResponseEntity.ok("Logged out successfully");
    }

    private void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken,
        Long refreshExpirationTime) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_COOKIE_NAME, refreshToken)
            .maxAge(refreshExpirationTime)
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }
}
