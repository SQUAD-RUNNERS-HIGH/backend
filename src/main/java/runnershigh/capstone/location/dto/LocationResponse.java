package runnershigh.capstone.location.dto;

import lombok.Builder;

@Builder
public record LocationResponse(
    String country,
    String province,
    String city,
    String dong,

    String specificLocation
) {

}
