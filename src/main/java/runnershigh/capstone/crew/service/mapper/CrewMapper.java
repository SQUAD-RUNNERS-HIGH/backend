package runnershigh.capstone.crew.service.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.dto.CrewCreateRequest;
import runnershigh.capstone.crew.dto.CrewDetailResponse;
import runnershigh.capstone.crew.dto.CrewParticipantsDetailsResponse;
import runnershigh.capstone.crewparticipant.domain.CrewParticipant;
import runnershigh.capstone.user.domain.User;

@Component
public class CrewMapper {

    public Crew toCrew(User crewLeader, CrewCreateRequest crewCreateRequest) {
        return Crew.builder()
            .name(crewCreateRequest.name())
            .description(crewCreateRequest.description())
            .maxCapacity(crewCreateRequest.maxCapacity())
            .image(crewCreateRequest.image())
//            .crewLocation(
//                new CrewLocation(crewCreateRequest.latitude(), crewCreateRequest.longitude()))
            .crewLeader(crewLeader)
            .crewParticipant(new HashSet<>())
            .build();
    }

    public CrewDetailResponse toCrewDetailResponse(Crew crew) {
        return CrewDetailResponse.builder()
            .name(crew.getName())
            .description(crew.getDescription())
            .maxCapacity(crew.getMaxCapacity())
            .userCount(crew.getUserCount())
            .image(crew.getImage())
            .crewLeaderName(crew.getCrewLeader().getUsername())
            .build();
    }

    public Set<CrewParticipantsDetailsResponse> toCrewParticipantsDetailsResponse(
        Set<CrewParticipant> crewParticipants) {
        return crewParticipants.stream()
            .map(participant -> CrewParticipantsDetailsResponse.builder()
                .username(participant.getParticipant().getUsername())
                .build())
            .collect(Collectors.toSet());
    }
}
