package runnershigh.capstone.crew.dto;

import lombok.Builder;

@Builder
public record CrewDetailResponse(
    String name,
    String description,
    int maxCapacity,
    int userCount,
    String image,
    String crewLeaderName
) {

}
