package runnershigh.capstone.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.jwt.service.JwtExtractor;
import runnershigh.capstone.user.dto.UserProfileRequest;
import runnershigh.capstone.user.dto.UserRegisterRequest;
import runnershigh.capstone.user.dto.UserResponse;
import runnershigh.capstone.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtExtractor jwtExtractor;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }

    @GetMapping
    public UserResponse getProfile(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        String refineToken = accessToken.replace(BEARER_PREFIX, "").trim();
        log.info(refineToken);
        String userId = jwtExtractor.extractUserIdByAccessToken(refineToken);
        log.info("userController userId : {}", userId);
        return userService.getProfile(userId);
    }

    @PutMapping
    public UserResponse updateProfile(HttpServletRequest request,
        @RequestBody UserProfileRequest userProfileRequest) {
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        String jwtToken = accessToken.replace(BEARER_PREFIX, "").trim();
        String userId = jwtExtractor.extractUserIdByAccessToken(jwtToken);
        return userService.updateProfile(userId, userProfileRequest);
    }


}
