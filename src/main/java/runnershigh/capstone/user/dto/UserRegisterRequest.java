package runnershigh.capstone.user.dto;

import runnershigh.capstone.location.dto.LocationRequest;

public record UserRegisterRequest(
    String loginId,
    String password,
    String username,
    UserPhysicalRequest physical,
    LocationRequest location
) {

}
