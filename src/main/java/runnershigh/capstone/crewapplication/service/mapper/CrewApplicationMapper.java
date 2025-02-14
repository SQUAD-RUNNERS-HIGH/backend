package runnershigh.capstone.crewapplication.service.mapper;

import org.springframework.stereotype.Component;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crewapplication.domain.CrewApplication;
import runnershigh.capstone.user.domain.User;

@Component
public class CrewApplicationMapper {

    public CrewApplication toCrewApplication(User applicant, Crew crew) {
        return CrewApplication.builder()
            .crew(crew)
            .applicant(applicant)
            .build();
    }
}
