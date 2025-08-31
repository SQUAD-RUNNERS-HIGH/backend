package runnershigh.capstone.user.dto;

import lombok.Builder;
import runnershigh.capstone.location.dto.LocationWithCoordinatesResponse;

@Builder
public record UserProfileResponse(
    String loginId,
    String username,
    UserPhysicalResponse physical,
    LocationWithCoordinatesResponse userLocation
) {

}
