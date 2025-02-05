package runnershigh.capstone.jwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
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
import runnershigh.capstone.jwt.util.CookieUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class JwtController {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final CookieUtil cookieUtil;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest,
        HttpServletResponse response) {
        LoginResponse loginResponse = jwtService.login(loginRequest.loginId(),
            loginRequest.password());

        response.setHeader(AuthConstants.AUTHORIZATION_HEADER.getValue(),
            AuthConstants.BEARER_PREFIX.getValue() + loginResponse.accessToken());

        cookieUtil.setRefreshTokenCookie(response, loginResponse.refreshToken(),
            jwtProperties.getRefreshExpirationTime());

        return loginResponse;
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
}
