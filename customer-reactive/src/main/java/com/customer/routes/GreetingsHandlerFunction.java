package com.customer.routes;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class GreetingsHandlerFunction implements HandlerFunction<ServerResponse> {
    @Override
    public Mono<ServerResponse> handle(final ServerRequest serverRequest) {
        return ok().syncBody("Hodor!");
    }
}
