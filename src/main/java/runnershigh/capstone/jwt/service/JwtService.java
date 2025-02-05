package runnershigh.capstone.jwt.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.jwt.domain.RefreshToken;
import runnershigh.capstone.jwt.dto.LoginResponse;
import runnershigh.capstone.jwt.repository.RefreshTokenRepository;
import runnershigh.capstone.jwt.util.JwtGenerator;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtService {

    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginResponse login(String loginId, String password) {

        Optional<User> existUser = userRepository.findByLoginId(loginId);

        if (existUser.isPresent() && existUser.get().getPassword().equals(password)) {

            String userId = String.valueOf(existUser.get().getId());

            String accessToken = jwtGenerator.generateAccessToken(userId);
            String refreshToken = jwtGenerator.generateRefreshToken(userId);

            refreshTokenRepository.deleteRefreshTokenByLoginId(loginId);
            refreshTokenRepository.save(new RefreshToken(refreshToken, loginId));

            return new LoginResponse(accessToken, refreshToken);
        }

        return null;
    }

    public void logout(String userId) {
        refreshTokenRepository.deleteRefreshTokenByLoginId(userId);
    }


}
