package runnershigh.capstone.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.global.argumentresolver.AuthUser;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.jwt.config.JwtProperties;
import runnershigh.capstone.jwt.dto.LoginRequest;
import runnershigh.capstone.jwt.dto.LogoutResponse;
import runnershigh.capstone.jwt.dto.TokenResponse;
import runnershigh.capstone.jwt.enums.AuthConstants;
import runnershigh.capstone.jwt.service.JwtService;
import runnershigh.capstone.jwt.util.CookieUtil;
import runnershigh.capstone.user.exception.UserNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "JWT [인증 & 인가]")
public class JwtController {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final CookieUtil cookieUtil;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "JWT 로그인")
    public TokenResponse login(@RequestBody LoginRequest loginRequest,
        HttpServletResponse response) {

        TokenResponse tokenResponse = jwtService.login(loginRequest.loginId(),
            loginRequest.password());

        setHeaderAndRefreshTokenCookie(response, tokenResponse);

        return tokenResponse;
    }

//    @GetMapping("/user")
//    public TokenResponse getUserFromToken(@AuthUser Long userId) {
//
//        if (Objects.isNull(userId)) {
//            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
//        }
//        return ResponseEntity.ok("Authenticated userId : " + userId);
//    }

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃", description = "JWT 로그아웃")
    public LogoutResponse logout(@AuthUser Long userId, HttpServletResponse response) {
        if (Objects.isNull(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        jwtService.logout(userId);

        cookieUtil.clearRefreshTokenCookie(response);

        return new LogoutResponse(userId);
    }

    @PostMapping("/refresh")
    @Operation(summary = "AccessToken 재발급", description = "현재 토큰을 바탕으로 AccessToken 재발급")
    public TokenResponse refresh(@Parameter(hidden = true) @AuthUser Long userId,
        HttpServletResponse response) {
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
