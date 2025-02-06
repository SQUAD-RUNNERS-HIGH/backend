package runnershigh.capstone.jwt.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {

    @Value("${jwt.secretKey.accessToken}")
    private String accessSecretKey;

    @Value("${jwt.secretKey.refreshToken}")
    private String refreshSecretKey;

    @Value("${jwt.expiration.accessToken}")
    private Long accessExpirationTime;

    @Value("${jwt.expiration.refreshToken}")
    private Long refreshExpirationTime;
}
