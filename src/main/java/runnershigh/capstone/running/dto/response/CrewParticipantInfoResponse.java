package runnershigh.capstone.running.dto.response;


import java.util.List;
import runnershigh.capstone.running.repository.CrewRunningRedisRepository.ReadyStatus;

public record CrewParticipantInfoResponse(
    List<CrewParticipantInfo> nearByParticipants
) {

    public record CrewParticipantInfo(
        String userId,
        boolean isReady,
        String username,
        double longitude,
        double latitude
        ){

    }

}
