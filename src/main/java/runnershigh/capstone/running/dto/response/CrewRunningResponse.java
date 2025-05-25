package runnershigh.capstone.running.dto.response;

import runnershigh.capstone.running.dto.RunningStatus;

public record CrewRunningResponse(
    RunningStatus runningStatus,
    String userId,
    double longitude,
    double latitude,
    double progress
) {

}
