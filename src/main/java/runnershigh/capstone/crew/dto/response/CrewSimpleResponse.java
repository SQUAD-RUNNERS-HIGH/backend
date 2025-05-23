package runnershigh.capstone.crew.dto.response;

import lombok.Builder;

@Builder
public record CrewSimpleResponse(
    Long crewId,
    String name,
    String description,
    long userCount,
    String image
) {

}
