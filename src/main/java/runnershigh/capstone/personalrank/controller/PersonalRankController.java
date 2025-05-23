package runnershigh.capstone.personalrank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.personalrank.dto.PersonalRankSliceResponse;
import runnershigh.capstone.personalrank.service.PersonalRankService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/personal-ranks")
@Tag(name = "경쟁자와 뛰기 경쟁자 랭킹")
public class PersonalRankController {

    private final PersonalRankService personalRankService;

    @GetMapping("/courses/{courseId}")
    @Operation(summary = "경쟁자와 뛰기 경쟁자 랭킹 조회", description = "랭킹을 무한 스크롤 방식으로 방식으로 제공합니다. 페이지는 0부터 "
        + "시작합니다.")
    public PersonalRankSliceResponse findPersonalRanks(
        @PathVariable String courseId, @RequestParam Integer page, @RequestParam Integer size){
        return personalRankService.findPersonalRankSlice(courseId,page,size);
    }
}
