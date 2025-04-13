package runnershigh.capstone.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.geocoding.service.GeocodingService;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.jwt.util.PBKDF2Util;
import runnershigh.capstone.user.domain.Physical;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.domain.UserLocation;
import runnershigh.capstone.user.dto.UserLocationRequest;
import runnershigh.capstone.user.dto.UserProfileRequest;
import runnershigh.capstone.user.dto.UserRegisterRequest;
import runnershigh.capstone.user.dto.UserResponse;
import runnershigh.capstone.user.exception.UserNotFoundException;
import runnershigh.capstone.user.repository.UserRepository;
import runnershigh.capstone.user.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final GeocodingService geocodingService;

    @Transactional
    public UserResponse register(UserRegisterRequest userRegisterRequest) {

        validateRegisterRequest(userRegisterRequest);

        String salt = PBKDF2Util.generateSalt();
        String hashedPassword = PBKDF2Util.hashPassword(userRegisterRequest.password(), salt);

        FormattedAddressResponse addressResponse = getFormattedAddressResponse(
            userRegisterRequest.location());

        User user = userMapper.toUser(userRegisterRequest, addressResponse, hashedPassword, salt);

        userRepository.save(user);

        return new UserResponse(user.getLoginId(), user.getUsername(),
            userMapper.toUserPhysicalResponse(user.getPhysical()),
            userMapper.toUserLocationResponse(user.getUserLocation()));
    }

    @Transactional
    public UserResponse updateProfile(Long userId, UserProfileRequest userProfileRequest) {
        User user = getUser(userId);
        Physical physicalRequest = userMapper.toPhysical(userProfileRequest.physical());
        FormattedAddressResponse addressResponse = getFormattedAddressResponse(
            userProfileRequest.location());
        UserLocation userLocation = userMapper.toUserLocation(addressResponse);
        user.updateProfile(userProfileRequest.password(), userProfileRequest.username(),
            physicalRequest, userLocation);

        return new UserResponse(user.getLoginId(), user.getUsername(),
            userMapper.toUserPhysicalResponse(user.getPhysical()),
            userMapper.toUserLocationResponse(user.getUserLocation()));
    }

    @Transactional(readOnly = true)
    public UserResponse getProfile(Long userId) {
        User user = getUser(userId);
        return new UserResponse(user.getLoginId(), user.getUsername(),
            userMapper.toUserPhysicalResponse(user.getPhysical()),
            userMapper.toUserLocationResponse(user.getUserLocation()));
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
            ErrorCode.USER_NOT_FOUND));
    }

    private void validateRegisterRequest(UserRegisterRequest userRegisterRequest) {
        if (userRepository.existsByLoginId(userRegisterRequest.loginId())) {
            throw new UserNotFoundException(ErrorCode.LOGIN_ID_DUPLICATION);
        }

        if (userRepository.existsByUsername(userRegisterRequest.username())) {
            throw new UserNotFoundException(ErrorCode.USERNAME_DUPLICATION);
        }
    }

    private FormattedAddressResponse getFormattedAddressResponse(
        UserLocationRequest userLocationRequest) {

        return geocodingService.getFormattedAddress(
            userLocationRequest.latitude(), userLocationRequest.longitude());
    }


}
