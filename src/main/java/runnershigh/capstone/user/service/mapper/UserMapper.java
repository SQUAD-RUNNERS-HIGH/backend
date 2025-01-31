package runnershigh.capstone.user.service.mapper;


import org.springframework.stereotype.Component;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.dto.UserRegisterRequest;

@Component
public class UserMapper {

    public User toUser(UserRegisterRequest userRegisterRequest) {
        return User.builder()
            .loginId(userRegisterRequest.loginId())
            .password(userRegisterRequest.password())
            .username(userRegisterRequest.username())
            .physical(userRegisterRequest.physical())
            .build();
    }
}
