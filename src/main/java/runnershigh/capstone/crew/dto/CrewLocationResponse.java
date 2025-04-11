package runnershigh.capstone.crew.dto;

import lombok.Builder;

@Builder
public record CrewLocationResponse(
    String country,
    String province,
    String city,
    String dong
) {

}
