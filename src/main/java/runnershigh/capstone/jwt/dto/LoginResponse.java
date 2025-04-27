package runnershigh.capstone.jwt.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
    TokenResponse tokenResponse,
    Long userId,
    String userName
) {

}
