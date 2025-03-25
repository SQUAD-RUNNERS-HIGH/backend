package runnershigh.capstone.personalrunninghistory.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.personalrunninghistory.domain.PersonalRunningHistory;
import runnershigh.capstone.personalrunninghistory.dto.PersonalRunningHistoryResponse;
import runnershigh.capstone.personalrunninghistory.exception.HistoryNotFoundException;
import runnershigh.capstone.personalrunninghistory.repository.PersonalRunningHistoryRepository;

@Service
@RequiredArgsConstructor
public class PersonalRunningHistoryService {

    private final PersonalRunningHistoryRepository personalRunningHistoryRepository;

    public PersonalRunningHistoryResponse getPersonalRunningHistory(final String historyId){
        final PersonalRunningHistory history =
            personalRunningHistoryRepository.findById(
                new ObjectId(historyId))
            .orElseThrow(() -> new HistoryNotFoundException(ErrorCode.HISTORY_NOT_FOUND));
        return new PersonalRunningHistoryResponse(history.getProgress(), history.getRunningTime());
    }
}
