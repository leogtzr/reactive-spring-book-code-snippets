package com.chat.config;

import lombok.SneakyThrows;

import com.chat.domain.Connection;
import com.chat.domain.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Configuration
class ChatWebsocketConfiguration {

    // <1>
    ChatWebsocketConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // <2>
    private final Map<String, Connection> sessions = new ConcurrentHashMap<>();

    // <3>
    private final BlockingQueue<Message> messages = new LinkedBlockingDeque<>();

    private final ObjectMapper objectMapper;

    @Bean
    WebSocketHandler chatWsh() {

        // <4>
        var messagesToBroadcast = Flux.<Message>create(sink -> {
            var submit = Executors.newSingleThreadExecutor().submit(() -> {
                while (true) {
                    try {
                        sink.next(this.messages.take());
                    } catch (final InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            sink.onCancel(() -> submit.cancel(true));
        }) //
                .share();

        return session -> { // <5>

            var sessionId = session.getId();

            this.sessions.put(sessionId, new Connection(sessionId, session));

            var in = session // <6>
                    .receive() //
                    .map(WebSocketMessage::getPayloadAsText) //
                    .map(this::messageFromJson) //
                    .map(msg -> new Message(sessionId, msg.getText(), new Date())) //
                    .map(this.messages::offer)//
                    .doFinally(st -> { // <7>
                        if (st.equals(SignalType.ON_COMPLETE)) {//
                            this.sessions.remove(sessionId);//
                        }
                    }); //

            var out = messagesToBroadcast // <8>
                    .map(this::jsonFromMessage)//
                    .map(session::textMessage);

            return session.send(out).and(in);
        };
    }

    // <9>
    @SneakyThrows
    private Message messageFromJson(final String json) {
        return this.objectMapper.readValue(json, Message.class);
    }

    @SneakyThrows
    private String jsonFromMessage(final Message msg) {
        return this.objectMapper.writeValueAsString(msg);
    }

    @Bean
    public HandlerMapping chatHm() {
        return new SimpleUrlHandlerMapping() {
            {
                this.setUrlMap(Map.of("/ws/chat", chatWsh()));
                this.setOrder(2);
            }
        };
    }

}
