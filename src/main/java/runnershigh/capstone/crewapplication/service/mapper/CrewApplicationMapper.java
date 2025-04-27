package runnershigh.capstone.crewapplication.service.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import runnershigh.capstone.crew.domain.Crew;
import runnershigh.capstone.crewapplication.domain.CrewApplication;
import runnershigh.capstone.crewapplication.dto.CrewApplicationsListResponse;
import runnershigh.capstone.crewapplication.dto.CrewApplicationsResponse;
import runnershigh.capstone.user.domain.User;

@Component
public class CrewApplicationMapper {

    public CrewApplication toCrewApplication(User applicant, Crew crew) {
        return CrewApplication.builder()
            .crew(crew)
            .applicant(applicant)
            .build();
    }

    public CrewApplicationsResponse toCrewApplicationsResponse(
        CrewApplication crewApplication) {
        return CrewApplicationsResponse.builder()
            .id(crewApplication.getId())
            .username(crewApplication.getApplicant().getUsername())
            .applicationDate(crewApplication.getApplicationDate())
            .build();
    }

    public CrewApplicationsListResponse toCrewApplicationsListResponse(
        List<CrewApplication> crewApplications
    ) {
        return CrewApplicationsListResponse.builder()
            .applicantResponse(
                crewApplications.stream()
                    .map(this::toCrewApplicationsResponse)
                    .toList()
            )
            .build();
    }
}
