package runnershigh.capstone.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.jwt.util.PBKDF2Util;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.dto.UserLocationRequest;
import runnershigh.capstone.user.dto.UserLocationResponse;
import runnershigh.capstone.user.dto.UserProfileRequest;
import runnershigh.capstone.user.dto.UserRegisterRequest;
import runnershigh.capstone.user.dto.UserResponse;
import runnershigh.capstone.user.exception.UserNotFoundException;
import runnershigh.capstone.user.repository.UserRepository;
import runnershigh.capstone.user.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserResponse register(UserRegisterRequest userRegisterRequest) {

        String salt = PBKDF2Util.generateSalt();
        String hashedPassword = PBKDF2Util.hashPassword(userRegisterRequest.password(), salt);

        User user = userMapper.toUser(userRegisterRequest, hashedPassword, salt);
        userRepository.save(user);

        return new UserResponse(user.getLoginId(), user.getUsername(), user.getPhysical());
    }

    @Transactional
    public UserResponse updateProfile(Long userId, UserProfileRequest userProfileRequest) {
        User user = getUser(userId);
        user.updateProfile(userProfileRequest);
        return new UserResponse(user.getLoginId(), user.getUsername(), user.getPhysical());
    }

    public UserResponse getProfile(Long userId) {
        User user = getUser(userId);
        return new UserResponse(user.getLoginId(), user.getUsername(), user.getPhysical());
    }

    @Transactional
    public UserLocationResponse saveUserLocation(Long userId,
        UserLocationRequest userLocationRequest) {
        User user = getUser(userId);

        user.getUserLocation().saveUserLocation(userLocationRequest.latitude(),
            userLocationRequest.longitude());

        log.info(user.getUserLocation().getCellParentToken());
        return new UserLocationResponse(user.getUserLocation().getCellParentToken());
    }

    public User getUser(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
            ErrorCode.USER_NOT_FOUND));
    }


}
