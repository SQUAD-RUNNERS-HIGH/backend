package runnershigh.capstone.running.dto.response;

import runnershigh.capstone.running.dto.RunningStatus;

public record CompetitorRunningResponse(
    RunningStatus runningStatus,
    Double longitude,
    Double latitude
) {

}
