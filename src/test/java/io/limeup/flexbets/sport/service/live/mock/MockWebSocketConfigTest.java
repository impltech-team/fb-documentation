package io.limeup.flexbets.sport.service.live.mock;

import org.junit.jupiter.api.Test;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;

import static org.mockito.Mockito.*;

class MockWebSocketConfigTest {

    @Test
    void registerWebSocketHandlersShouldRegisterLiveHandler() {
        MockWebSocketController controller = mock(MockWebSocketController.class);
        WebSocketHandlerRegistry registry = mock(WebSocketHandlerRegistry.class);
        WebSocketHandlerRegistration registration = mock(WebSocketHandlerRegistration.class);

        when(registry.addHandler(controller, "/live")).thenReturn(registration);
        when(registration.setAllowedOrigins("*")).thenReturn(registration);

        MockWebSocketConfig config = new MockWebSocketConfig(controller);
        config.registerWebSocketHandlers(registry);

        verify(registry).addHandler(controller, "/live");
        verify(registration).setAllowedOrigins("*");
    }
}


