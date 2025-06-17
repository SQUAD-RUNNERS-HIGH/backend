package runnershigh.capstone.global.config.stomp;

import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class RunningStompHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        Map<String, Object> attributes) throws Exception {
        String uri = request.getURI().toString();

        if (uri.contains("/ws-running")) {
            attributes.put("endpoint", "running");
        }
        return true;
    }

    @Override
    public void afterHandshake(final ServerHttpRequest request, final ServerHttpResponse response,
        final WebSocketHandler wsHandler,
        final Exception exception) {

    }
}
