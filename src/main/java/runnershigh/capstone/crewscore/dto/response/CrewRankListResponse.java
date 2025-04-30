package runnershigh.capstone.crewscore.dto.response;

import java.util.List;

public record CrewRankListResponse(
    List<CrewRankResponse> crewRankResponses
) {
    public record CrewRankResponse(
        Long crewId,
        Double score
    ){

    }
}
