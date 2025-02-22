package runnershigh.capstone.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import runnershigh.capstone.jwt.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    void deleteByUserId(Long userId);

}

