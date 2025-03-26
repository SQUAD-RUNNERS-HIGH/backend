package runnershigh.capstone.user.dto;


import runnershigh.capstone.user.domain.Physical;
import runnershigh.capstone.user.domain.UserLocation;

public record UserResponse(
    String loginId,
    String username,
    Physical physical,
    UserLocation userlocation
) {

}
