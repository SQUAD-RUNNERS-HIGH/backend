package runnershigh.capstone.user.dto;

import lombok.Builder;

@Builder
public record UserLocationResponse(
    String country,
    String province,
    String city,
    String dong,

    String specificLocation
) {

}
