package runnershigh.capstone.personalrunninghistory.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.personalrank.domain.PersonalRank;
import runnershigh.capstone.personalrank.service.PersonalRankService;
import runnershigh.capstone.personalrunninghistory.domain.PersonalRunningHistory;
import runnershigh.capstone.personalrunninghistory.dto.PersonalRunningHistoryRequest;
import runnershigh.capstone.personalrunninghistory.dto.PersonalRunningHistoryResponse;
import runnershigh.capstone.personalrunninghistory.exception.HistoryNotFoundException;
import runnershigh.capstone.personalrunninghistory.repository.PersonalRunningHistoryRepository;
import runnershigh.capstone.user.domain.User;
import runnershigh.capstone.user.service.UserService;

@Service
@RequiredArgsConstructor
public class PersonalRunningHistoryService {

    private final PersonalRunningHistoryRepository personalRunningHistoryRepository;
    private final PersonalRankService personalRankService;
    private final UserService userService;

    public PersonalRunningHistoryResponse getCompetitorRunningHistory(final String historyId){
        final PersonalRunningHistory history =
            personalRunningHistoryRepository.findById(
                new ObjectId(historyId))
            .orElseThrow(() -> new HistoryNotFoundException(ErrorCode.HISTORY_NOT_FOUND));
        return new PersonalRunningHistoryResponse(history.getProgress(), history.getRunningTime()
            ,history.getUserName());
    }

    public void savePersonalRunningHistory(final PersonalRunningHistoryRequest request,
        final Long userId){
        final User user = userService.getUser(userId);
        final PersonalRunningHistory history = new PersonalRunningHistory(
            request.progress(), request.runningTime(), userId,
            user.getUsername());
        personalRunningHistoryRepository.save(history);
        personalRankService.savePersonalRank(history.getId().toString(),request,user);
    }
}
