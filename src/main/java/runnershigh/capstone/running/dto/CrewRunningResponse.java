package runnershigh.capstone.running.dto;

public record CrewRunningResponse(
    RunningStatus runningStatus,
    String userId,
    double longitude,
    double latitude
) {

}
