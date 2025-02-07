package runnershigh.capstone.jwt.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.jwt.domain.RefreshToken;
import runnershigh.capstone.jwt.dto.TokenResponse;
import runnershigh.capstone.jwt.repository.RefreshTokenRepository;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse login(String loginId, String password) {

        Optional<User> existUser = userRepository.findByLoginId(loginId);

        if (existUser.isPresent() && existUser.get().getPassword().equals(password)) {
            String userId = String.valueOf(existUser.get().getId());

            return generateAndReturnToken(userId);
        }

        return null;
    }

    public void logout(String userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    public TokenResponse refresh(String userId) {
        return generateAndReturnToken(userId);
    }

    public TokenResponse generateAndReturnToken(String userId) {
        String accessToken = jwtGenerator.generateAccessToken(userId);
        String refreshToken = jwtGenerator.generateRefreshToken(userId);

        log.info("Generated access token: {}", accessToken);
        log.info("Generated refresh token: {}", refreshToken);

        refreshTokenRepository.deleteByUserId(userId);
        refreshTokenRepository.save(new RefreshToken(refreshToken, userId));

        return new TokenResponse(accessToken, refreshToken);
    }


}
