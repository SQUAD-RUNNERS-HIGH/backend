package runnershigh.capstone.personalrank.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
import runnershigh.capstone.personalrunninghistory.dto.PersonalRunningHistoryRequest;
import runnershigh.capstone.user.domain.User;

@Service
@RequiredArgsConstructor
public class PersonalRankService {

    private final PersonalRankRepository personalRankRepository;
    private final PersonalRankMapper personalRankMapper;

    public PersonalRankSliceResponse findPersonalRankSlice(final String courseId,
        final Integer page, final Integer size){
        PageRequest pageRequest = PageRequest.of(page, size,
            Sort.by(Direction.DESC, "runningTime"));
        Slice<PersonalRank> personalRanks = personalRankRepository.findByCourseId(courseId,
            pageRequest);
        return personalRankMapper.toPersonalRankSliceResponse(personalRanks);
    }

    public void savePersonalRank(final String historyId,
        final PersonalRunningHistoryRequest request,final User user){
        PersonalRank personalRank = new PersonalRank(request.courseId(), historyId, user,
            request.runningTime());
        personalRankRepository.save(personalRank);
    }
}
