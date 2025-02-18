package runnershigh.capstone.personalrank.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record PersonalRankSliceResponse(
    boolean hasNext,
    int page,
    int size,
    List<PersonalRunningTime> personalRunningTimes
) {

}
