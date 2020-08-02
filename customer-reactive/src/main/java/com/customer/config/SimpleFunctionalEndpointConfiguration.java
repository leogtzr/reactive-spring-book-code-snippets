package com.customer.config;

import com.customer.routes.GreetingsHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class SimpleFunctionalEndpointConfiguration {

    // You can register as many beans of type RouterFunction<ServerResponse as you like. ...
    /*
        If I wanted to rewrite all the URLs or change the strings for the resource URIs, I’d
        need not look any further than this single bean definition.
     */
    @Bean
    public RouterFunction<ServerResponse> simple(final GreetingsHandlerFunction handler) {
        return route()
                .GET("/hello/{name}", request -> {
                    final var namePathVariable = request.pathVariable("name");
                    final var message = String.format("Hello %s!", namePathVariable);
                    return ok().bodyValue(message);
                })
                .GET("/hodor", handler)
                .GET("/sup", handler::handle)
                .build();
    }

}
