package runnershigh.capstone.crew.dto;

import lombok.Builder;
import runnershigh.capstone.location.dto.LocationResponse;

@Builder
public record CrewDetailResponse(
    String name,
    String description,
    int maxCapacity,
    int userCount,
    String image,
    String crewLeaderName,
    LocationResponse crewLocation
) {

}
