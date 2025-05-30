package runnershigh.capstone.running.dto.response;

import runnershigh.capstone.running.dto.RunningStatus;

public record CrewRunningResponse(
    RunningStatus runningStatus,
    String userId,
    String username,
    double longitude,
    double latitude,
    double progress
) {

}
