package runnershigh.capstone.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.jwt.util.JwtExtractor;
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

    @PostMapping
    public UserResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }

    @GetMapping
    public UserResponse getProfile(@RequestHeader(AUTHORIZATION_HEADER) String token) {
        String jwtToken = token.replace(BEARER_PREFIX, "").trim();
        String loginId = jwtExtractor.extractLoginIdByAccessToken(jwtToken);
        return userService.getProfile(loginId);
    }

    @PutMapping
    public UserResponse updateProfile(@RequestHeader(AUTHORIZATION_HEADER) String token,
        @RequestBody UserProfileRequest userProfileRequest) {
        String jwtToken = token.replace(BEARER_PREFIX, "").trim();
        String loginId = jwtExtractor.extractLoginIdByAccessToken(jwtToken);
        return userService.updateProfile(loginId, userProfileRequest);
    }


}
