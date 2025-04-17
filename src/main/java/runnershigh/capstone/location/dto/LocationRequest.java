package runnershigh.capstone.location.dto;

import lombok.Builder;

@Builder
public record LocationRequest(
    double latitude,
    double longitude,

    String specificLocation
) {

}
