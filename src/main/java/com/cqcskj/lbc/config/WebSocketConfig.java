package com.cqcskj.lbc.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/ws")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new CustomWebSocketHandler();
    }
    public class CustomWebSocketHandler extends TextWebSocketHandler {

        private static final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

        @Override
        public void afterConnectionEstablished(@NotNull WebSocketSession session) {
            sessions.add(session);
            broadcast("用户[" + session.getId() + "] 加入聊天室");
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) {
            String payload = message.getPayload();
            // 消息处理逻辑
            broadcast("用户[" + session.getId() + "]: " + payload);
        }

        private void broadcast(String message) {
            sessions.parallelStream().forEach(s -> {
                try {
                    if (s.isOpen()) s.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    // 异常处理
                }
            });
        }
    }
}
