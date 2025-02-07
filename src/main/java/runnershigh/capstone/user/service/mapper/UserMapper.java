package runnershigh.capstone.user.service.mapper;


import org.springframework.stereotype.Component;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.dto.UserRegisterRequest;

@Component
public class UserMapper {

    public User toUser(UserRegisterRequest userRegisterRequest, String hashedPassword,
        String salt) {
        return User.builder()
            .loginId(userRegisterRequest.loginId())
            .password(hashedPassword)
            .passwordSalt(salt)
            .username(userRegisterRequest.username())
            .physical(userRegisterRequest.physical())
            .build();
    }
}
