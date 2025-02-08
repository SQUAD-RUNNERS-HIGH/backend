package runnershigh.capstone.crew.dto;

public record CrewCreateRequest(
    String name,
    String description,
    int maxCapacity,
    double latitude,
    double longitude
) {

}
