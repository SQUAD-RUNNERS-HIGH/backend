package runnershigh.capstone.user.dto;


public record UserResponse(
    String loginId,
    String username,
    UserPhysicalResponse physical,
    UserLocationResponse location
) {

}
