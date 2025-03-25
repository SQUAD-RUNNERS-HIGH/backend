package runnershigh.capstone.personalrank.service.mapper;

import java.util.List;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import runnershigh.capstone.personalrank.domain.PersonalRank;
import runnershigh.capstone.personalrank.dto.PersonalRankSliceResponse;
import runnershigh.capstone.personalrank.dto.PersonalRankResponse;

@Component
public class PersonalRankMapper {

    private List<PersonalRankResponse> createPersonalRankResponse(
        final Slice<PersonalRank> personalRanks) {
        return personalRanks.getContent().stream()
            .map(r -> new PersonalRankResponse(r.getUserName(), r.toRunningTimeStringFormat(),r.getHistoryId()))
            .toList();
    }

    public PersonalRankSliceResponse toPersonalRankSliceResponse(
        Slice<PersonalRank> personalRanks) {
        return PersonalRankSliceResponse.builder()
            .hasNext(personalRanks.hasNext())
            .page(personalRanks.getNumber())
            .size(personalRanks.getContent().size())
            .personalRunningTimes(
                createPersonalRankResponse(personalRanks)
            )
            .build();
    }


}
