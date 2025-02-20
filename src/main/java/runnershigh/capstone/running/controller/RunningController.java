package runnershigh.capstone.running.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import runnershigh.capstone.running.dto.RunningInfo;

@RestController
public class RunningController {

    @MessageMapping("/{courseId}")
    @SendToUser("/queue/reply")
    public String personalRunning(@DestinationVariable("courseId") String courseId,@Payload
        RunningInfo runningInfo){
        return "success" + courseId;
    }
}
