package runnershigh.capstone.crewapplication.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record CrewApplicationsListResponse(
    List<CrewApplicationsResponse> applicantResponse
) {

}
