package runnershigh.capstone.crew.service.mapper;

import java.util.HashSet;
import org.springframework.stereotype.Component;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.domain.CrewLocation;
import runnershigh.capstone.crew.dto.CrewCreateRequest;
import runnershigh.capstone.crew.dto.CrewDetailResponse;
import runnershigh.capstone.user.domain.User;

@Component
public class CrewMapper {

    public Crew toCrew(User crewLeader, CrewCreateRequest crewCreateRequest) {
        return Crew.builder()
            .name(crewCreateRequest.name())
            .description(crewCreateRequest.description())
            .maxCapacity(crewCreateRequest.maxCapacity())
            .image(crewCreateRequest.image())
            .crewLocation(
                new CrewLocation(crewCreateRequest.latitude(), crewCreateRequest.longitude()))
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
}
