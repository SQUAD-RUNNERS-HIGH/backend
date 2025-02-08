package runnershigh.capstone.crew.service.mapper;

import org.springframework.stereotype.Component;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crew.domain.CrewLocation;
import runnershigh.capstone.crew.dto.CrewCreateRequest;
import runnershigh.capstone.user.domain.User;

@Component
public class CrewMapper {

    public Crew toCrew(User crewLeader, CrewCreateRequest crewCreateRequest) {
        return Crew.builder()
            .name(crewCreateRequest.name())
            .description(crewCreateRequest.description())
            .maxCapacity(crewCreateRequest.maxCapacity())
            .crewLocation(
                new CrewLocation(crewCreateRequest.latitude(), crewCreateRequest.longitude()))
            .crewLeader(crewLeader)
            .build();
    }
}
