package runnershigh.capstone.global.config.stomp;

import lombok.RequiredArgsConstructor;
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
public class JwtStompInterceptor implements ChannelInterceptor {

    private final JwtValidator jwtValidator;
    private final JwtExtractor jwtExtractor;

    private static final String NATIVE_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader(NATIVE_HEADER);
            if (authHeader.startsWith(BEARER_PREFIX)) {
                String token = authHeader.substring(7);
                if (jwtValidator.validateAccessToken(token)) {
                    Long userId = jwtExtractor.extractUserIdByAccessToken(token);
                    accessor.getSessionAttributes().put("userId", userId);
                }
            } else {
                throw new JwtNotFoundException(ErrorCode.INVALID_TOKEN);
            }
        }

        return message;
    }
}
