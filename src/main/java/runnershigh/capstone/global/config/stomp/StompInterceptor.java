package runnershigh.capstone.global.config.stomp;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.jwt.exception.JwtNotFoundException;
import runnershigh.capstone.jwt.service.JwtExtractor;
import runnershigh.capstone.jwt.service.JwtValidator;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompInterceptor implements ChannelInterceptor {

    private static final String NATIVE_AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtValidator jwtValidator;
    private final JwtExtractor jwtExtractor;

    private boolean isDestinationUserQueue(final StompHeaderAccessor accessor) {
        return accessor.getDestination().startsWith("/user/queue");
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("COMMAND : {} ", accessor.getCommand());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader(NATIVE_AUTHORIZATION_HEADER);
            if (authHeader.startsWith(BEARER_PREFIX)) {
                String token = authHeader.substring(7);
                if (jwtValidator.validateAccessToken(token)) {
                    Long userId = jwtExtractor.extractUserIdByAccessToken(token);
                    accessor.getSessionAttributes().put("userId", userId);
                } else {
                    throw new JwtNotFoundException(ErrorCode.INVALID_TOKEN);
                }
            } else {
                throw new JwtNotFoundException(ErrorCode.INVALID_TOKEN);
            }
        }
        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand()) && isDestinationUserQueue(accessor)) {
            String courseId = accessor.getFirstNativeHeader("CourseId");
            String crewId = accessor.getFirstNativeHeader("CrewId");
            log.info("CourseId:{}, CrewId:{}",courseId,crewId);
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            sessionAttributes.put("courseId", courseId);
            sessionAttributes.put("crewId", crewId);
        }
        return message;
    }
}
