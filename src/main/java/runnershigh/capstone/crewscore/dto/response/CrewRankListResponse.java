package runnershigh.capstone.crewscore.dto.response;

import java.util.List;

public record CrewRankListResponse(
    List<CrewRankResponse> crewRankResponses
) {
    public record CrewRankResponse(
        String crewId,
        Double score,
        String image,
        String crewName
    ){

    }
}
