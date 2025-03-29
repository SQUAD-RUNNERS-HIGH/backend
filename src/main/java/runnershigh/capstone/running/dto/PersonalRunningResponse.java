package runnershigh.capstone.running.dto;

import java.util.List;

public record PersonalRunningResponse(
    RunningStatus runningStatus,
    Double longitude,
    Double latitude
) {

}
