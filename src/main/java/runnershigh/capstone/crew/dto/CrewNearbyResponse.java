package runnershigh.capstone.crew.dto;

import lombok.Builder;

@Builder
public record CrewNearbyResponse(
    String name,
    String description,
    long userCount
) {

}
