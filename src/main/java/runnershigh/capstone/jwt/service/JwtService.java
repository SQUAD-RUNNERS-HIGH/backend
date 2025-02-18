package runnershigh.capstone.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.jwt.domain.RefreshToken;
import runnershigh.capstone.jwt.dto.TokenResponse;
import runnershigh.capstone.jwt.repository.RefreshTokenRepository;
import runnershigh.capstone.jwt.util.PBKDF2Util;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.exception.UserNotFoundException;
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

        User existUser = userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new UserNotFoundException(
                ErrorCode.USER_NOT_FOUND));

        if (isPasswordValid(password, existUser)) {

            Long userId = existUser.getId();

            return generateAndReturnToken(userId);
        }

        return null;
    }

    private static boolean isPasswordValid(String password, User existUser) {
        return PBKDF2Util.verifyPassword(password, existUser.getPasswordSalt(),
            existUser.getPassword()
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
