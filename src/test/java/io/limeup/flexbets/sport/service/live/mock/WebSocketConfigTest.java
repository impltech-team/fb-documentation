package io.limeup.flexbets.sport.service.live.mock;

import io.limeup.flexbets.sport.service.live.WebSocketController;
import io.limeup.flexbets.sport.service.live.WebSocketConfig;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;

import static org.mockito.Mockito.*;

class WebSocketConfigTest {

    @Test
    void registerWebSocketHandlersShouldRegisterLiveHandler() {
        WebSocketController controller = mock(WebSocketController.class);
        WebSocketHandlerRegistry registry = mock(WebSocketHandlerRegistry.class);
        WebSocketHandlerRegistration registration = mock(WebSocketHandlerRegistration.class);

        when(registry.addHandler(controller, "/live")).thenReturn(registration);
        when(registration.setAllowedOrigins("*")).thenReturn(registration);

        WebSocketConfig config = new WebSocketConfig(controller);
        config.registerWebSocketHandlers(registry);

        verify(registry).addHandler(controller, "/live");
        verify(registration).setAllowedOrigins("*");
    }
}


