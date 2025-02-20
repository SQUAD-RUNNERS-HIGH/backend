package runnershigh.capstone.global.config.stomp;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import runnershigh.capstone.jwt.service.JwtExtractor;
import runnershigh.capstone.jwt.service.JwtValidator;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtWebSocketInterceptor implements HandshakeInterceptor {

    private final JwtValidator jwtValidator;
    private final JwtExtractor jwtExtractor;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
        WebSocketHandler wsHandler, Map<String, Object> attributes) {
        log.info("called");
        String token = jwtExtractor.extractAccessToken((HttpServletRequest) request);
        if (jwtValidator.validateAccessToken(token)) {
            attributes.put("userId", jwtExtractor.extractUserIdByAccessToken(token));
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
        WebSocketHandler wsHandler, Exception exception) {
    }
}
