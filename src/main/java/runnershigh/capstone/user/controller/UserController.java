package runnershigh.capstone.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/user")
@Tag(name = "유저 [유저 CRUD & 위치]")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "회원 가입", description = "유저 정보를 받아, 유저 정보를 반환합니다.")
    public UserResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }

    @GetMapping
    @Operation(summary = "유저 프로필 조회", description = "유저 ID를 받아, 프로필 정보를 반환합니다.")
    public UserResponse getProfile(@Parameter(hidden = true) @AuthUser Long userId) {
        return userService.getProfile(userId);
    }

    @PutMapping
    @Operation(summary = "유저 프로필 수정", description = "유저 ID & 수정할 유저 정보를 받아, 수정된 프로필 정보를 반환합니다.")
    public UserResponse updateProfile(@Parameter(hidden = true) @AuthUser Long userId,
        @RequestBody UserProfileRequest userProfileRequest) {

        return userService.updateProfile(userId, userProfileRequest);
    }

    @PostMapping("/location")
    @Operation(summary = "유저 위치정보 저장", description = "유저 ID & 위치정보를 받아, CellToken 반환합니다.")
    public UserLocationResponse saveUserLocation(@Parameter(hidden = true) @AuthUser Long userId,
        @RequestBody UserLocationRequest userLocationRequest) {

        return userService.saveUserLocation(userId, userLocationRequest);
    }
}
