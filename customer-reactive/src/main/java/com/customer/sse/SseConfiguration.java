package com.customer.sse;

import com.customer.utils.IntervalMessageProducer;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Log4j2
@Configuration
public class SseConfiguration {

    private final String COUNT_PATH_VARIABLE = "count";

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route()
                .GET("/sse/{" + this.COUNT_PATH_VARIABLE + "}", this::handleSse)
                .build();
    }

    /*
    For a @RequestMapping example we would have used produces = MediaType.TEXT_EVENT_STREAM
     */
    private Mono<ServerResponse> handleSse(final ServerRequest r) {
        final var countPathVariable = Integer.parseInt(r.pathVariable(this.COUNT_PATH_VARIABLE));
        final var publisher = IntervalMessageProducer.produce(countPathVariable)
                .doOnComplete(() -> log.info("completed"))
                ;

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(publisher, String.class)
                ;
    }

}
