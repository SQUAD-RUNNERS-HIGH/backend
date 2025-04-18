package runnershigh.capstone.running.dto;

public record CrewParticipantInfoRequest(
    String userId,
    double latitude,
    double longitude,
    boolean isReady
) {

}
