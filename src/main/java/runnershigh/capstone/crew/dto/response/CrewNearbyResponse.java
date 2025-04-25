package runnershigh.capstone.crew.dto.response;

import lombok.Builder;

@Builder
public record CrewNearbyResponse(
    String name,
    String description,
    long userCount
) {

}
