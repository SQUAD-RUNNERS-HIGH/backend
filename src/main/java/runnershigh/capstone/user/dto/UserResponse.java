package runnershigh.capstone.user.dto;


import lombok.Builder;
import runnershigh.capstone.location.dto.LocationResponse;

@Builder
public record UserResponse(
    String loginId,
    String username,
    UserPhysicalResponse physical,
    LocationResponse userLocation
) {

}
