package runnershigh.capstone.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.geocoding.dto.GeocodingApiResponse;
import runnershigh.capstone.geocoding.service.GeocodingService;
import runnershigh.capstone.global.argumentresolver.AuthUser;
import runnershigh.capstone.user.dto.UserProfileRequest;
import runnershigh.capstone.user.dto.UserProfileResponse;
import runnershigh.capstone.user.dto.UserRegisterRequest;
import runnershigh.capstone.user.dto.UserResponse;
import runnershigh.capstone.user.dto.UsernameResponse;
import runnershigh.capstone.user.service.UserQueryService;
import runnershigh.capstone.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "유저 [유저 CRUD & 위치]")
public class UserController {

    private final UserService userService;
    private final UserQueryService userQueryService;
    private final GeocodingService geocodingService;

    @PostMapping("/register")
    @Operation(summary = "회원 가입", description = "유저 정보를 받아, 유저 정보를 반환합니다.")
    public UserResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }

    @GetMapping
    @Operation(summary = "유저 프로필 조회", description = "유저 ID를 받아, 프로필 정보를 반환합니다.")
    public UserProfileResponse getProfile(@Parameter(hidden = true) @AuthUser Long userId) {
        return userQueryService.getProfile(userId);
    }

    @PatchMapping
    @Operation(summary = "유저 프로필 수정", description = "유저 ID & 수정할 유저 정보를 받아, 수정된 프로필 정보를 반환합니다.")
    public UserResponse updateProfile(@Parameter(hidden = true) @AuthUser Long userId,
        @RequestBody UserProfileRequest userProfileRequest) {
        return userService.updateProfile(userId, userProfileRequest);
    }

    @GetMapping("/{userId}/username")
    @Operation(summary = "유저네임 조회", description = "유저의 ID를 받아, 유저네임을 반환합니다.")
    public UsernameResponse getUsername(@PathVariable Long userId) {
        return userQueryService.getUsername(userId);
    }

    @GetMapping("/location-test")
    @Operation(summary = "위치정보 -> 주소 변환 조회", description = "위도, 고도를 받아, 주소를 반환합니다. [임시 컨트롤러]")
    public GeocodingApiResponse getAddress(@RequestParam double latitude,
        double longitude) {
        return geocodingService.getGeocodingApiResponse(latitude, longitude);
    }

}
