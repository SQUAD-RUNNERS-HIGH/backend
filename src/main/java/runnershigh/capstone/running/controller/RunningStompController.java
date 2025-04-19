package runnershigh.capstone.running.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.running.dto.CompetitorRunningInfoRequest;
import runnershigh.capstone.running.dto.CompetitorRunningResponse;
import runnershigh.capstone.running.dto.CrewParticipantInfoRequest;
import runnershigh.capstone.running.dto.CrewParticipantInfoResponse;
import runnershigh.capstone.running.dto.CrewRunningInfoRequest;
import runnershigh.capstone.running.dto.CrewRunningResponse;
import runnershigh.capstone.running.service.CompetitorRunningService;
import runnershigh.capstone.running.service.CrewRunningService;

@RestController
@RequiredArgsConstructor
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
        return competitorRunningService.calculateCrewRunning(request, courseId, crewId);
    }

    @MessageMapping("/crew-participant/course/{courseId}/crew/{crewId}")
    @SendTo("/topic/crew-participant/course/{courseId}/crew/{crewId}")
    public CrewParticipantInfoResponse sendLocation(@DestinationVariable("courseId") String courseId,
        @DestinationVariable("crewId") String crewId,
        @Payload CrewParticipantInfoRequest request) {
        return crewRunningService.sendReadyLocation(request, courseId, crewId);
    }
}
