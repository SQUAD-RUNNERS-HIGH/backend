package runnershigh.capstone.jwt.dto;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {

}
