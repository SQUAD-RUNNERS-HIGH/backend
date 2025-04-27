package runnershigh.capstone.running.dto.response;


public record CrewParticipantInfoResponse(
    String userId,
    String userName,
    double longitude,
    double latitude,
    boolean isReady,
    boolean startSignal
) {

}
