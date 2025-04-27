package runnershigh.capstone.running.dto.request;

public record CrewParticipantInfoRequest(
    String userId,
    String userName,
    double latitude,
    double longitude,
    boolean isReady
) {

}
