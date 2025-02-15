package runnershigh.capstone.crew.dto;

import lombok.Builder;

@Builder
public record CrewDetailResponse(
    String name,
    String description,
    int maxCapacity,
    String image,
    String crewLeaderName
) {

}
