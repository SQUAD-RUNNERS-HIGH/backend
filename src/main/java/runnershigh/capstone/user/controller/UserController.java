package runnershigh.capstone.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.global.argumentresolver.AuthUser;
import runnershigh.capstone.user.dto.UserLocationRequest;
import runnershigh.capstone.user.dto.UserLocationResponse;
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

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }

    @GetMapping
    public UserResponse getProfile(@AuthUser Long userId) {
        return userService.getProfile(userId);
    }

    @PutMapping
    public UserResponse updateProfile(@AuthUser Long userId,
        @RequestBody UserProfileRequest userProfileRequest) {

        return userService.updateProfile(userId, userProfileRequest);
    }

    @PostMapping("/location")
    public UserLocationResponse saveUserLocation(@AuthUser Long userId,
        @RequestBody UserLocationRequest userLocationRequest) {

        return userService.saveUserLocation(userId, userLocationRequest);
    }
}
