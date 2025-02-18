package runnershigh.capstone.personalrank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import runnershigh.capstone.personalrank.domain.PersonalRank;
import runnershigh.capstone.personalrank.dto.PersonalRankSliceResponse;
import runnershigh.capstone.personalrank.repository.PersonalRankRepository;
import runnershigh.capstone.personalrank.service.mapper.PersonalRankMapper;

@Service
@RequiredArgsConstructor
public class PersonalRankService {

    private final PersonalRankRepository personalRankRepository;
    private final PersonalRankMapper personalRankMapper;

    public void updateRank(){
    }

    public PersonalRankSliceResponse findPersonalRankSlice(final String courseId,
        final Integer page, final Integer size){
        PageRequest pageRequest = PageRequest.of(page, size,
            Sort.by(Direction.DESC, "runningTime"));
        Slice<PersonalRank> personalRanks = personalRankRepository.findByCourseId(courseId,
            pageRequest);
        return personalRankMapper.toPersonalRankSliceResponse(personalRanks);
    }
}
