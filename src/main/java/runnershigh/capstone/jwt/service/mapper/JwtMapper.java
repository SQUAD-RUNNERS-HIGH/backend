package runnershigh.capstone.jwt.service.mapper;

import org.springframework.stereotype.Component;
import runnershigh.capstone.jwt.dto.LoginResponse;
import runnershigh.capstone.jwt.dto.TokenResponse;

@Component
public class JwtMapper {

    public LoginResponse toLoginResponse(TokenResponse tokenResponse, Long userId) {
        return LoginResponse.builder()
            .tokenResponse(tokenResponse)
            .userId(userId)
            .build();
    }
}
