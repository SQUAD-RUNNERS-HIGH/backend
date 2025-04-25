package runnershigh.capstone.crew.dto.request;

import runnershigh.capstone.location.dto.LocationRequest;

public record CrewCreateRequest(
    String name,
    String description,
    int maxCapacity,
    String image,
    LocationRequest crewLocation
) {

}
