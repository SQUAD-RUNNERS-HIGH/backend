package runnershigh.capstone.user.dto;


public record UserPhysicalRequest(
    String gender,
    Long age,
    double height,
    double weight
) {

}
