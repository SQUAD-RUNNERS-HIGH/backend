package runnershigh.capstone.user.service.mapper;


import org.springframework.stereotype.Component;
import runnershigh.capstone.geocoding.dto.FormattedAddressResponse;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.domain.UserLocation;
import runnershigh.capstone.user.dto.UserRegisterRequest;

@Component
public class UserMapper {

    public User toUser(UserRegisterRequest userRegisterRequest,
        FormattedAddressResponse formattedAddressResponse,
        String hashedPassword,
        String salt) {
        return User.builder()
            .loginId(userRegisterRequest.loginId())
            .password(hashedPassword)
            .passwordSalt(salt)
            .username(userRegisterRequest.username())
            .physical(userRegisterRequest.physical())
            .userLocation(new UserLocation(formattedAddressResponse.country(),
                formattedAddressResponse.province(),
                formattedAddressResponse.city(), formattedAddressResponse.dong()))
            .build();
    }
}
