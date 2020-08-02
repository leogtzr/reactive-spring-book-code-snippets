package com.customer.config;

import com.customer.routes.NestedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class NestedFunctionalEndpointConfiguration {

    @Bean
    public RouterFunction<ServerResponse> nested(final NestedHandler nestedHandler) {
        final var jsonRP = accept(MediaType.APPLICATION_JSON)
                .or(accept(MediaType.APPLICATION_JSON_UTF8));
        final var sseRP = accept(MediaType.TEXT_EVENT_STREAM);

        return route()
                .nest(
                        path("/nested"), builder -> builder
                        .nest(jsonRP, nestedBuilder -> nestedBuilder
                        .GET("/{pv}", nestedHandler::pathVariable)
                        .GET("", nestedHandler::noPathVariable)
                        ).add(route(sseRP, nestedHandler::sse))
                )
                .build()
                ;
    }

}
