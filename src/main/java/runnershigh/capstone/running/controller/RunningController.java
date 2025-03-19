package runnershigh.capstone.running.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.running.dto.PersonalRunningInfo;
import runnershigh.capstone.running.dto.PersonalRunningResponse;
import runnershigh.capstone.running.service.PersonalRunningService;

@RestController
@RequiredArgsConstructor
public class RunningController {

    private final PersonalRunningService personalRunningService;

    @MessageMapping("/{courseId}")
    @SendToUser("/queue/reply")
    public PersonalRunningResponse personalRunning(@DestinationVariable("courseId") String courseId,@Payload
    PersonalRunningInfo personalRunningInfo){
        return personalRunningService.calculatePersonalRunning(personalRunningInfo);
    }
}
