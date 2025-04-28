package runnershigh.capstone.crew.dto.request;

import runnershigh.capstone.location.dto.LocationRequest;

public record CrewUpdateRequest(
    String name,
    String description,
    int maxCapacity,
    LocationRequest crewLocation
) {

}
