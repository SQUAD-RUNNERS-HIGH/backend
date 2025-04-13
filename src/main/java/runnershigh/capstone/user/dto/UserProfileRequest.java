package runnershigh.capstone.user.dto;

public record UserProfileRequest(
    String password,
    String username,
    UserPhysicalRequest physical,
    UserLocationRequest location
) {

}
