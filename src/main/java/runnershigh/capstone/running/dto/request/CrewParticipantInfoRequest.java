package runnershigh.capstone.running.dto.request;

public record CrewParticipantInfoRequest(
    String userId,
    double latitude,
    double longitude,
    boolean isReady
) {

}
