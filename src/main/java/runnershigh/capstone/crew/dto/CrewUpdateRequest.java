package runnershigh.capstone.crew.dto;

import runnershigh.capstone.location.dto.LocationRequest;

public record CrewUpdateRequest(
    String name,
    String description,
    int maxCapacity,
    String image,
    LocationRequest location
) {

}
