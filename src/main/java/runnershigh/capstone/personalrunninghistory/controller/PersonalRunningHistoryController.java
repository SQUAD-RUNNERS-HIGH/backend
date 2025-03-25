package runnershigh.capstone.personalrunninghistory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.global.argumentresolver.AuthUser;
import runnershigh.capstone.personalrunninghistory.dto.PersonalRunningHistoryResponse;
import runnershigh.capstone.personalrunninghistory.service.PersonalRunningHistoryService;

@RestController
@RequestMapping("/api/personal/history")
@RequiredArgsConstructor
public class PersonalRunningHistoryController {

    private final PersonalRunningHistoryService personalRunningHistoryService;

    @GetMapping("/histories/{historyId}/courses/{courseId}/")
    public PersonalRunningHistoryResponse createPersonalRunning(@AuthUser Long userId,
        @PathVariable String courseId,
        @PathVariable String historyId) {
        return personalRunningHistoryService.getPersonalRunningHistory(historyId);
    }

}
