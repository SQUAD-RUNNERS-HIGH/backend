package runnershigh.capstone.crewapplication.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record CrewApplicationsResponse(
    Long id,
    String username,
    LocalDate applicationDate
) {

}
