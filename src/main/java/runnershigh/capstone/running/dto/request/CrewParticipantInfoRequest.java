package runnershigh.capstone.running.dto.request;

public record CrewParticipantInfoRequest(
    String userId,
    String username,
    double latitude,
    double longitude,
    boolean isReady
) {

}
