package runnershigh.capstone.personalrank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runnershigh.capstone.personalrank.domain.PersonalRank;
import runnershigh.capstone.personalrank.dto.PersonalRankSliceResponse;
import runnershigh.capstone.personalrank.repository.PersonalRankRepository;
import runnershigh.capstone.personalrank.service.mapper.PersonalRankMapper;
import runnershigh.capstone.personalrunninghistory.dto.PersonalRunningHistoryRequest;
import runnershigh.capstone.user.domain.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalRankService {

    private final PersonalRankRepository personalRankRepository;
    private final PersonalRankMapper personalRankMapper;

    public PersonalRankSliceResponse findPersonalRankSlice(final String courseId,
        final Integer page, final Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size,
            Sort.by(Direction.DESC, "runningTime"));
        Slice<PersonalRank> personalRanks = personalRankRepository.findByCourseId(courseId,
            pageRequest);
        return personalRankMapper.toPersonalRankSliceResponse(personalRanks);
    }

    @Transactional
    public void compareRunningTime(final String historyId,
        final PersonalRunningHistoryRequest request, final User user) {
        personalRankRepository.findByUserAndCourseId(user, request.courseId())
            .ifPresentOrElse(r -> r.changeRunningTime(request.runningTime()),
                () -> personalRankRepository.save(
                    new PersonalRank(request.courseId(), historyId, user,
                        request.runningTime())));
    }
}
