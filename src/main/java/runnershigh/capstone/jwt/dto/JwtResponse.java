package runnershigh.capstone.jwt.dto;

public record JwtResponse(
    String accessToken,
    String refreshToken
) {

}
