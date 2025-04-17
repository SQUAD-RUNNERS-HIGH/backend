package runnershigh.capstone.user.dto;


import runnershigh.capstone.location.dto.LocationResponse;

public record UserResponse(
    String loginId,
    String username,
    UserPhysicalResponse physical,
    LocationResponse location
) {

}
