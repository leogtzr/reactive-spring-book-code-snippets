package com.customer.routes;

import com.customer.utils.IntervalMessageProducer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class NestedHandler {

    public Mono<ServerResponse> sse(final ServerRequest req) {
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(IntervalMessageProducer.produce(), String.class);
    }

    public Mono<ServerResponse> pathVariable(final ServerRequest r) {
        return ok().syncBody(greet(Optional.of(r.pathVariable("pv"))));
    }

    public Mono<ServerResponse> noPathVariable(final ServerRequest r) {
        return ok().syncBody(greet(Optional.ofNullable(null)));
    }

    private Map<String, String> greet(final Optional<String> name) {
        final var finalName = name.orElse("world");
        return Map.of("message", String.format("Hello %s!", finalName));
    }

}
