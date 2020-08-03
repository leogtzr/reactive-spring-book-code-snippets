package com.customer.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Log4j2
@Configuration
public class LowercaseWebConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routerFunctionFilters() {
        final var uuidKey = UUID.class.getName();
        return RouterFunctions.route()
                // Defines two endpoints ...
                .GET("/hi/{name}", this::handle)
                .GET("/hello/{name}", this::handle)
                .filter((req, next) -> {
                    log.info(".filter(): before");
                    final var reply = next.handle(req);
                    log.info(".filter(): after");
                    return reply;
                })
                .before(request -> {
                    log.info(".before()");
                    request.attributes().put(uuidKey, UUID.randomUUID());
                    return request;
                })
                .after((serverRequest, serverResponse) -> {
                    log.info(".after()");
                    log.info("UUID: " + serverRequest.attributes().get(uuidKey));
                    return serverResponse;
                })
                .onError(NullPointerException.class, (e, request) -> ServerResponse.badRequest().build())
                .build();
    }

    private Mono<ServerResponse> handle(final ServerRequest serverRequest) {
        return ok().syncBody(
                String.format("Hello, %s!", serverRequest.pathVariable("name"))
        );
    }

}

/*
The HandlerFilterFunction is invoked earlier than .before() and earlier than .after(). In this example,
I also configure a onError() callback that returns an HTTP 400 if something should go wrong with the
request. The onError() method lets me keep tedious error handling and cleanup logic separate from
the endpoint handler functions themselves; they can throw an Exception and have it bubble up to the
centralized error-handling routine in onError.
 */