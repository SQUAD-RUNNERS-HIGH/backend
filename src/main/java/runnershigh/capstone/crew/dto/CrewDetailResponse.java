package runnershigh.capstone.crew.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record CrewDetailResponse(
    String name,
    String description,
    int maxCapacity,
    String image,
    String crewLeaderName
) {

}
