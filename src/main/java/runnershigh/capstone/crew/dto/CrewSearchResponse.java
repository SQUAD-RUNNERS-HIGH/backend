package runnershigh.capstone.crew.dto;

import java.util.List;
import runnershigh.capstone.crew.domain.Crew;

public record CrewSearchResponse(
    List<Crew> crews
) {

}
