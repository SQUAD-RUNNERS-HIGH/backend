package runnershigh.capstone.user.dto;

public record UserRegisterRequest(
    String loginId,
    String password,
    String username,
    UserPhysicalRequest physical,
    double latitude,
    double longitude
) {

}
