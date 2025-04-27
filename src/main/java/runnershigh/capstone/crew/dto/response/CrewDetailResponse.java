package runnershigh.capstone.crew.dto.response;

import lombok.Builder;
import runnershigh.capstone.location.dto.LocationResponse;

@Builder
public record CrewDetailResponse(
    String name,
    String description,
    int maxCapacity,
    int userCount,
    String image,
    Long crewRank,
    String crewLeaderName,
    String crewUserRole,
    LocationResponse crewLocation
) {

}
