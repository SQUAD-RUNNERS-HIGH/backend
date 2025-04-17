package runnershigh.capstone.running.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.running.dto.PersonalRunningInfo;
import runnershigh.capstone.running.dto.PersonalRunningResponse;
import runnershigh.capstone.running.service.CrewRunningService;
import runnershigh.capstone.running.service.PersonalRunningService;

@RestController
@RequiredArgsConstructor
public class RunningStompController {

    private final PersonalRunningService personalRunningService;
    private final CrewRunningService crewRunningService;

    @MessageMapping("/course/{courseId}")
    @SendToUser("/queue/reply")
    public PersonalRunningResponse personalRunning(@DestinationVariable("courseId") String courseId,@Payload
    PersonalRunningInfo personalRunningInfo){
        return personalRunningService.calculatePersonalRunning(personalRunningInfo,courseId);
    }

    @MessageMapping("/course/{courseId}/crew/{crewId}")
    @SendTo("/crew/{crewId}")
    public PersonalRunningResponse crewRunning(@DestinationVariable("courseId") String courseId,
        @DestinationVariable("crewId") Long crewId,
        @Payload PersonalRunningInfo personalRunningInfo){
        return personalRunningService.calculatePersonalRunning(personalRunningInfo,courseId);
    }
}
