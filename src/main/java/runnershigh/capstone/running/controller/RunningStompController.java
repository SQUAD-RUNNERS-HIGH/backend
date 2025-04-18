package runnershigh.capstone.running.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.running.dto.CrewParticipantInfoRequest;
import runnershigh.capstone.running.dto.CrewParticipantInfoResponse;
import runnershigh.capstone.running.dto.CrewRunningInfoRequest;
import runnershigh.capstone.running.dto.CrewRunningResponse;
import runnershigh.capstone.running.dto.PersonalRunningInfoRequest;
import runnershigh.capstone.running.dto.PersonalRunningResponse;
import runnershigh.capstone.running.service.RunningService;

@RestController
@RequiredArgsConstructor
public class RunningStompController {

    private final RunningService runningService;

    @MessageMapping("/course/{courseId}")
    @SendToUser("/queue/reply")
    public PersonalRunningResponse personalRunning(@DestinationVariable("courseId") String courseId,@Payload
    PersonalRunningInfoRequest personalRunningInfoRequest){
        return runningService.calculatePersonalRunning(personalRunningInfoRequest,courseId);
    }

//    @MessageMapping("/crew-run/course/{courseId}/crew/{crewId}")
//    @SendTo("/topic/crew-run/course/{courseId}/crew/{crewId}")
//    public CrewRunningResponse crewRunning(@DestinationVariable("courseId") String courseId,
//        @DestinationVariable("crewId") Long crewId,
//        @Payload CrewRunningInfoRequest request){
//        return runningService.calculateCrewRunning(request,courseId,crewId);
//    }

    @MessageMapping("/crew-participant/course/{courseId}/crew/{crewId}")
    @SendTo("/topic/crew-participant/course/{courseId}/crew/{crewId}")
    public CrewParticipantInfoResponse sendLocation(@DestinationVariable("courseId") String courseId,
        @DestinationVariable("crewId") String crewId,
        @Payload CrewParticipantInfoRequest request){
        return runningService.sendLocation(request,courseId,crewId);
    }
}
