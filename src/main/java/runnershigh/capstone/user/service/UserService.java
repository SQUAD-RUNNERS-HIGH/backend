package runnershigh.capstone.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.jwt.util.PBKDF2Util;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.dto.UserProfileRequest;
import runnershigh.capstone.user.dto.UserRegisterRequest;
import runnershigh.capstone.user.dto.UserResponse;
import runnershigh.capstone.user.repository.UserRepository;
import runnershigh.capstone.user.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    public UserResponse updateProfile(String userId, UserProfileRequest userProfileRequest) {

        User user = userRepository.findById(Long.parseLong(userId)).orElse(null);
        assert user != null;
        user.updateProfile(userProfileRequest);
        return new UserResponse(user.getLoginId(), user.getUsername(), user.getPhysical());
    }

    public UserResponse getProfile(String userId) {

        User user = userRepository.findById(Long.parseLong(userId)).orElse(null);
        assert user != null;
        return new UserResponse(user.getLoginId(), user.getUsername(), user.getPhysical());
    }


}
