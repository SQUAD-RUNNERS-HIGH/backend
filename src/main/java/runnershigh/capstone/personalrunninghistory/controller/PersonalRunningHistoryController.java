package runnershigh.capstone.personalrunninghistory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.global.argumentresolver.AuthUser;
import runnershigh.capstone.personalrunninghistory.dto.PersonalRunningHistoryRequest;
import runnershigh.capstone.personalrunninghistory.dto.PersonalRunningHistoryResponse;
import runnershigh.capstone.personalrunninghistory.service.PersonalRunningHistoryService;

@RestController
@RequestMapping("/api/personal/history")
@RequiredArgsConstructor
@Tag(name = "경쟁자의 과거 러닝 기록")
public class PersonalRunningHistoryController {

    private final PersonalRunningHistoryService personalRunningHistoryService;

    @PostMapping("/histories/{historyId}/courses/{courseId}/")
    @Operation(summary = "경쟁자의 과거 러닝 기록 조회", description = "경쟁자 랭킹 조회 시 응답한 historyId를 통해 세부 정보를 "
        + "조회할 수 있습니다.[진행률, 총 러닝 진행 시간]")
    public PersonalRunningHistoryResponse getCompetitorRunningHistory(@AuthUser Long userId,
        @PathVariable String courseId,
        @PathVariable String historyId) {
        return personalRunningHistoryService.getCompetitorRunningHistory(historyId);
    }

    @PostMapping
    @Operation(summary = "현 사용자의 러닝 기록 저장", description = "현재 사용자의 러닝 기록을 저장합니다.")
    public void createPersonalRunningHistory(@AuthUser Long userId,@RequestBody
        PersonalRunningHistoryRequest request){
        personalRunningHistoryService.savePersonalRunningHistory(request,userId);
    }
}
