package com.customer.echo;

import com.customer.utils.IntervalMessageProducer;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.Map;

@Log4j2
@Configuration
public class EchoWebsocketConfiguration {

    @Bean
    public HandlerMapping echoHm() {
        return new SimpleUrlHandlerMapping() {
            {
                this.setOrder(1);
                this.setUrlMap(Map.of("/ws/echo", echoWsh()));
            }
        };
    }

    @Bean
    public WebSocketHandler echoWsh() {
        return session -> {
            final Flux<WebSocketMessage> out = IntervalMessageProducer
                    .produce()
                    .doOnNext(log::info)
                    .map(session::textMessage)
                    .doFinally(signalType -> log.info("outbound connection: " + signalType));

            final Flux<String> in = session
                    .receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .doFinally(signalType -> {
                        log.info("inbound connection: " + signalType);
                        if (signalType.equals(SignalType.ON_COMPLETE)) {
                            session.close().subscribe();
                        }
                    }).doOnNext(log::info);

            return session.send(out).and(in);
        };
    }

}
