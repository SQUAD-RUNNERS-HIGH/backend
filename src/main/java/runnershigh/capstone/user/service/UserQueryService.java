package runnershigh.capstone.user.service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.dto.UserResponse;
import runnershigh.capstone.user.dto.UsernameResponse;
import runnershigh.capstone.user.repository.UserRepository;
import runnershigh.capstone.user.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserResponse getProfile(Long userId) {
        User user = userService.getUser(userId);
        return userMapper.toUserResponse(user);
    }

    public UsernameResponse getUsername(Long userId) {
        User user = userService.getUser(userId);
        return new UsernameResponse(user.getUsername());
    }

    public Map<Long, String> getUsernamesByIds(Set<Long> ids) {
        return userRepository.findAllById(ids).stream()
            .collect(Collectors.toMap(User::getId, User::getUsername));
    }

}
