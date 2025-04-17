package runnershigh.capstone.crew.dto;

import lombok.Builder;

@Builder
public record CrewLocationRequest(
    double latitude,
    double longitude,

    String specificLocation
) {

}
