package runnershigh.capstone.jwt.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.jwt.domain.RefreshToken;
import runnershigh.capstone.jwt.dto.JwtResponse;
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

    public JwtResponse login(String loginId, String password) {

        Optional<User> exitUser = userRepository.findByLoginId(loginId);

        if (exitUser.isPresent() && exitUser.get().getPassword().equals(password)) {
            String accessToken = jwtGenerator.generateAccessToken(loginId);
            String refreshToken = jwtGenerator.generateRefreshToken(loginId);

            refreshTokenRepository.deleteRefreshTokenByLoginId(loginId);
            refreshTokenRepository.save(new RefreshToken(refreshToken, loginId));

            return new JwtResponse(accessToken, refreshToken);
        }

        return null;
    }

    public void logout(String loginId) {
        refreshTokenRepository.deleteRefreshTokenByLoginId(loginId);
    }


}
