package runnershigh.capstone.personalrank.dto;

import java.util.List;
import lombok.Builder;
import runnershigh.capstone.personalrank.domain.PersonalRank;

@Builder
public record PersonalRankSliceResponse(
    boolean hasNext,
    int page,
    int size,
    List<PersonalRankResponse> personalRunningTimes
) {

}
