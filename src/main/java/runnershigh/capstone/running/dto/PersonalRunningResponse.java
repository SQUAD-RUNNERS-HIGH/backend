package runnershigh.capstone.running.dto;

public record PersonalRunningResponse(
    RunningStatus runningStatus,
    Double longitude,
    Double latitude
) {

}
