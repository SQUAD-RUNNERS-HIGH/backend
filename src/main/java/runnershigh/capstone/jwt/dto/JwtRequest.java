package runnershigh.capstone.jwt.dto;

public record JwtRequest(
    String loginId,
    String password
) {

}
