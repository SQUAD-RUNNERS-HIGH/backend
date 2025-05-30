package runnershigh.capstone.running.dto.request;

public record CrewRunningInfoRequest(
    String userId,
    String username,
    double longitude,
    double latitude,
    double progress
) {

}
