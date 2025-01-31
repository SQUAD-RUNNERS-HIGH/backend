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
import runnershigh.capstone.jwt.util.JwtProvider;
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
    private final JwtProvider jwtProvider;

    @PostMapping
    public UserResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }

    @GetMapping
    public UserResponse getProfile(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "").trim();
        String loginId = jwtProvider.extractLoginIdByAccessToken(jwtToken);
        return userService.getProfile(loginId);
    }

    @PutMapping
    public UserResponse updateProfile(@RequestHeader("Authorization") String token,
        @RequestBody UserProfileRequest userProfileRequest) {
        String jwtToken = token.replace("Bearer ", "").trim();
        String loginId = jwtProvider.extractLoginIdByAccessToken(jwtToken);
        return userService.updateProfile(loginId, userProfileRequest);
    }


}
