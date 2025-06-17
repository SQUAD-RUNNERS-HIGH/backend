package runnershigh.capstone.running.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.running.dto.request.CompetitorRunningInfoRequest;
import runnershigh.capstone.running.dto.response.CompetitorRunningResponse;
import runnershigh.capstone.running.dto.request.CrewParticipantInfoRequest;
import runnershigh.capstone.running.dto.response.CrewParticipantInfoResponse;
import runnershigh.capstone.running.dto.request.CrewRunningInfoRequest;
import runnershigh.capstone.running.dto.response.CrewRunningResponse;
import runnershigh.capstone.running.service.CompetitorRunningService;
import runnershigh.capstone.running.service.CrewRunningService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RunningStompController {

    private final CompetitorRunningService competitorRunningService;
    private final CrewRunningService crewRunningService;

    @MessageMapping("/course/{courseId}")
    @SendToUser("/queue/reply")
    public CompetitorRunningResponse competitorRunningCalculate(@DestinationVariable("courseId") String courseId,
        @Payload CompetitorRunningInfoRequest competitorRunningInfoRequest) {
        return competitorRunningService.calculateCompetitorRunning(competitorRunningInfoRequest, courseId);
    }

    @MessageMapping("/crew-run/course/{courseId}/crew/{crewId}")
    @SendTo("/topic/crew-run/course/{courseId}/crew/{crewId}")
    public CrewRunningResponse crewRunning(@DestinationVariable("courseId") String courseId,
        @DestinationVariable("crewId") String crewId,
        @Payload CrewRunningInfoRequest request) {
        return crewRunningService.calculateCrewRunning(request, courseId, crewId);
    }

    @MessageMapping("/crew-participant/course/{courseId}/crew/{crewId}")
    @SendToUser("/queue/reply")
    public CrewParticipantInfoResponse sendLocation(@DestinationVariable("courseId") String courseId,
        @DestinationVariable("crewId") String crewId,
        @Payload CrewParticipantInfoRequest request) {
        log.info("request:{}",request);
        return crewRunningService.sendReadyLocation(request, courseId, crewId);
    }
}
