package runnershigh.capstone.crewscore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.crewscore.dto.request.CrewScoreRequest;
import runnershigh.capstone.crewscore.service.CrewScoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crew-rank")
public class CrewScoreController {

    private final CrewScoreService crewScoreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCrewScore(@RequestBody final CrewScoreRequest request){
        crewScoreService.updateCrewScore(request);
    }
}
