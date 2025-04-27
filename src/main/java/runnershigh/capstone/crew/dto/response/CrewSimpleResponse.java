package runnershigh.capstone.crew.dto.response;

import lombok.Builder;

@Builder
public record CrewSimpleResponse(
    String name,
    String description,
    long userCount
) {

}
