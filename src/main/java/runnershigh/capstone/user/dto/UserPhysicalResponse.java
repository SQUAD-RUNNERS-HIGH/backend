package runnershigh.capstone.user.dto;

import lombok.Builder;

@Builder
public record UserPhysicalResponse(
    String gender,
    Long age,
    double height,
    double weight
) {

}
