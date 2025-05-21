package runnershigh.capstone.crewparticipant.dto;

import java.util.List;

public record MyCrewResponse(
    List<MyCrew> myCrews
) {

    public record MyCrew(
        Long crewId,
        String crewName,
        Integer numberOfParticipants,
        String crewUserRole,
        String image
    ) {

    }

}
