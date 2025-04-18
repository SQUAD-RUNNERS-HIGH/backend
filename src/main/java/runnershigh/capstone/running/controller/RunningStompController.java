package runnershigh.capstone.running.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.running.dto.CrewParticipantInfoRequest;
import runnershigh.capstone.running.dto.CrewParticipantInfoResponse;
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
//    @SendTo("/crew-run/course/{courseId}/crew/{crewId}")
//    public CrewRunningResponse crewRunning(@DestinationVariable("courseId") String courseId,
//        @DestinationVariable("crewId") Long crewId,
//        @Payload CrewRunningInfo personalRunningInfo){
//        return runningService.calculateCrewRunning(personalRunningInfo,courseId);
//    }

    @MessageMapping("/crew-participant/course/{courseId}/crew/{crewId}")
    @SendTo("/topic/crew-participant/course/{courseId}/crew/{crewId}")
    public CrewParticipantInfoResponse findNearByCrewParticipants(@DestinationVariable("courseId") String courseId,
        @DestinationVariable("crewId") String crewId,
        @Payload CrewParticipantInfoRequest crewParticipantInfoRequest){
        return runningService.findNearByCrewParticipants(crewParticipantInfoRequest,courseId,crewId);
    }
}
