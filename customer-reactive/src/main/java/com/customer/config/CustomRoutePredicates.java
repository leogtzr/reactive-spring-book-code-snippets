package com.customer.config;

import com.customer.routes.CaseInsensitiveRequestPredicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Set;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Log4j2
@Configuration
public class CustomRoutePredicates {

    private final HandlerFunction<ServerResponse> handler =
        request -> ok().syncBody(
                "Hello, " + request.queryParam("name").orElse("world") + "!"
        );

    @Bean
    public RouterFunction<ServerResponse> customRequestPredicates() {

        /*
        This example demonstrates that you can compose (or negate, or both) RequestPredicate
implementations. A RequestPredicate can express conditions like "match an HTTP GET request and
match a custom request predicate." Here we substitute a method reference for a RequestPredicate.
         */
        final var aPeculiarRequestPredicate = GET("/test")
                .and(accept(MediaType.APPLICATION_JSON_UTF8))
                .and(this::isRequestForAValidUid);

/*
Here, with the static i() factory method that I’ve created, I wrap and adapt a RequestPredicate with
another implementation that lower-cases the request’s URI. Tada! Case-insensitive request
matching. We’ll explore the implementation details momentarily.
 */
        final var caseInsensitiveRequestPredicate = CaseInsensitiveRequestPredicate.i(GET("/greetings/{name}"));

        return route()
                .add(route(aPeculiarRequestPredicate, this.handler))
                .add(route(caseInsensitiveRequestPredicate, this.handler))
                .build()
                ;
    }

    private boolean isRequestForAValidUid(final ServerRequest req) {
        final var goodUids = Set.of("1", "2", "3");
        return req
                .queryParam("uid")
                .map(goodUids::contains)
                .orElse(false);
    }

}
