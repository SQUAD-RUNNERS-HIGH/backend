package runnershigh.capstone.global.config.stomp;

import java.util.Map;
import java.util.Objects;
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

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("COMMAND : {} ", accessor.getCommand());
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            setUserIdToSession(accessor, sessionAttributes);
            setCrewRunningToSession(sessionAttributes, accessor);
        }
        return message;
    }

    private void setUserIdToSession(final StompHeaderAccessor accessor, final Map<String, Object> sessionAttributes) {
        String authHeader = accessor.getFirstNativeHeader(NATIVE_AUTHORIZATION_HEADER);
        if (authHeader.startsWith(BEARER_PREFIX)) {
            String token = authHeader.substring(7);
            setUserId(token, sessionAttributes);
        } else {
            throw new JwtNotFoundException(ErrorCode.INVALID_TOKEN);
        }
    }

    private void setCrewRunningToSession(final Map<String, Object> sessionAttributes,
        final StompHeaderAccessor accessor) {
        String endpoint = (String) sessionAttributes.get("endpoint");
        boolean isCrewRun = Boolean.parseBoolean(accessor.getFirstNativeHeader("CrewRun"));
        if (Objects.nonNull(endpoint) && endpoint.equals("running") && isCrewRun) {
            String crewId = accessor.getFirstNativeHeader("CrewId");
            String courseId = accessor.getFirstNativeHeader("CourseId");
            sessionAttributes.put("crewId",crewId);
            sessionAttributes.put("courseId",courseId);
        }
    }

    private void setUserId(final String token, final Map<String,Object> sessionAttributes) {
        if (jwtValidator.validateAccessToken(token)) {
            Long userId = jwtExtractor.extractUserIdByAccessToken(token);
            sessionAttributes.put("userId", userId);
        } else {
            throw new JwtNotFoundException(ErrorCode.INVALID_TOKEN);
        }
    }
}
