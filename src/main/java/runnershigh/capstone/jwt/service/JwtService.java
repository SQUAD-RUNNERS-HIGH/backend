package runnershigh.capstone.jwt.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.jwt.domain.RefreshToken;
import runnershigh.capstone.jwt.dto.TokenResponse;
import runnershigh.capstone.jwt.repository.RefreshTokenRepository;
import runnershigh.capstone.jwt.util.PBKDF2Util;
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

        boolean isPasswordValid = isPasswordValid(password, existUser);

        if (isPasswordValid) {

            Long userId = existUser.get().getId();

            return generateAndReturnToken(userId);
        }

        return null;
    }

    private static boolean isPasswordValid(String password, Optional<User> existUser) {
        boolean isUserPresent = existUser.isPresent();
        return isUserPresent && PBKDF2Util.verifyPassword(password,
            existUser.get().getPasswordSalt(),
            existUser.get().getPassword()
        );
    }

    public void logout(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    public TokenResponse refresh(Long userId) {
        return generateAndReturnToken(userId);
    }

    private TokenResponse generateAndReturnToken(Long userId) {
        String accessToken = jwtGenerator.generateAccessToken(userId);
        String refreshToken = jwtGenerator.generateRefreshToken(userId);

        log.info("Generated access token: {}", accessToken);
        log.info("Generated refresh token: {}", refreshToken);

        refreshTokenRepository.deleteByUserId(userId);
        refreshTokenRepository.save(new RefreshToken(refreshToken, userId));

        return new TokenResponse(accessToken, refreshToken);
    }


}
