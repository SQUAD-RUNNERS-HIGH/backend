package runnershigh.capstone.location.dto;

import lombok.Builder;

@Builder
public record LocationWithCoordinatesResponse(
    double latitude,
    double longitude,

    String specificLocation
) {

}
