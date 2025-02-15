package runnershigh.capstone.crew.dto;

public record CrewUpdateRequest(
    String name,
    String description,
    int maxCapacity,
    String image
) {

}
