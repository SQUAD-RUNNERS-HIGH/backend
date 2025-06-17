package runnershigh.capstone.global.config.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import runnershigh.capstone.running.repository.CrewRunningRedisRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class StompDisconnectListener {

    private final CrewRunningRedisRepository repository;

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Long userId = (Long)accessor.getSessionAttributes().get("userId");
        String courseId = (String)accessor.getSessionAttributes().get("courseId");
        String crewId = (String)accessor.getSessionAttributes().get("crewId");
        repository.removeLocation(courseId,crewId,userId.toString());
        repository.removeReadyStatus(courseId,crewId,userId.toString());
    }
}