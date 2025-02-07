package runnershigh.capstone.jwt.dto;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {

}
