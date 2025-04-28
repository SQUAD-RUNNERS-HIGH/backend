package runnershigh.capstone.running.dto.request;

public record CrewRunningInfoRequest(
    String userId,
    double longitude,
    double latitude,
    double progress
) {

}
