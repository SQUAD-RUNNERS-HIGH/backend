package runnershigh.capstone.running.dto;


public record CrewParticipantInfoResponse(
    String userId,
    double longitude,
    double latitude,
    boolean isReady,
    boolean startSignal
) {

}
