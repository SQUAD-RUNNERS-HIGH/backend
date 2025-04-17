package runnershigh.capstone.crew.dto;

public record CrewCreateRequest(
    String name,
    String description,
    int maxCapacity,
    String image,
    CrewLocationRequest location
) {

}
