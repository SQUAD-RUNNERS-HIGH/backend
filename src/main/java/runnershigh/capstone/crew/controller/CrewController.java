package runnershigh.capstone.crew.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.crew.dto.CrewCreateRequest;
import runnershigh.capstone.crew.dto.CrewCreateResponse;
import runnershigh.capstone.crew.dto.CrewSearchResponse;
import runnershigh.capstone.crew.service.CrewService;
import runnershigh.capstone.global.argumentresolver.AuthUser;

@RestController
@RequestMapping("/crew")
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    @PostMapping
    public CrewCreateResponse createCrew(@AuthUser Long crewLeaderId,
        @RequestBody CrewCreateRequest crewCreateRequest) {
        return crewService.createCrew(crewLeaderId, crewCreateRequest);
    }

    @GetMapping("/surround")
    public CrewSearchResponse searchCrew(@AuthUser Long userId) {
        return crewService.searchCrew(userId);
    }
}
