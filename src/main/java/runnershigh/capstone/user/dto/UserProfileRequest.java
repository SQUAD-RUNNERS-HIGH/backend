package runnershigh.capstone.user.dto;

import runnershigh.capstone.location.dto.LocationRequest;

public record UserProfileRequest(
    String password,
    String username,
    UserPhysicalRequest physical,
    LocationRequest userLocation
) {

}
