package io.limeup.flexbets.sport.service.live.mock;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class MockWebSocketConfig implements WebSocketConfigurer {

    private final MockWebSocketController webSocketController;

    public MockWebSocketConfig(MockWebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketController, "/live")
                .setAllowedOrigins("*");
    }
}

