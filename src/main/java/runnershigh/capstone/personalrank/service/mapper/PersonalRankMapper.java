package runnershigh.capstone.personalrank.service.mapper;

import java.util.List;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import runnershigh.capstone.personalrank.domain.PersonalRank;
import runnershigh.capstone.personalrank.dto.PersonalRankSliceResponse;
import runnershigh.capstone.personalrank.dto.PersonalRunningTime;

@Component
public class PersonalRankMapper {

    private List<PersonalRunningTime> getPersonalRunningTimes(
        final Slice<PersonalRank> personalRanks) {
        return personalRanks.getContent().stream()
            .map(r -> new PersonalRunningTime(r.getUserName(), r.toRunningTimeStringFormat()))
            .toList();
    }

    public PersonalRankSliceResponse toPersonalRankSliceResponse(
        Slice<PersonalRank> personalRanks) {
        return PersonalRankSliceResponse.builder()
            .hasNext(personalRanks.hasNext())
            .page(personalRanks.getNumber())
            .size(personalRanks.getContent().size())
            .personalRunningTimes(
                getPersonalRunningTimes(personalRanks)
            )
            .build();
    }


}
