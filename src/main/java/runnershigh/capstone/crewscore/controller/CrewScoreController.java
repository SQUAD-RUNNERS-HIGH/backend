package runnershigh.capstone.crewscore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.crewscore.dto.request.CrewScoreRequest;
import runnershigh.capstone.crewscore.dto.response.CrewRankListResponse;
import runnershigh.capstone.crewscore.service.CrewScoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crew-rank")
@Tag(name = "크루 랭킹 (점수)")
public class CrewScoreController {

    private final CrewScoreService crewScoreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "크루 달리기 후 기록 저장")
    public void saveCrewScore(@RequestBody final CrewScoreRequest request){
        crewScoreService.updateCrewScore(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "크루 랭킹 조회")
    public CrewRankListResponse findCrewRanks(@RequestParam final Long size){
        return crewScoreService.getRanks(size);
    }
}
