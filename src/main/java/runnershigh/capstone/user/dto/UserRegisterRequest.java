package runnershigh.capstone.user.dto;

public record UserRegisterRequest(
    String loginId,
    String password,
    String username,
    UserPhysicalRequest physical,
    UserLocationRequest location
) {

}
