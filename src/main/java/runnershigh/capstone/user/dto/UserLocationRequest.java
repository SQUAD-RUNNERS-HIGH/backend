package runnershigh.capstone.user.dto;

import lombok.Builder;

@Builder
public record UserLocationRequest(
    double latitude,
    double longitude,

    String specificLocation
) {

}
