package com.views.config;

import com.views.repository.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CustomerViewEndpointConfiguration {

    @Bean
    public RouterFunction<ServerResponse> customerViews(final CustomerRepository repository) {
        return route()
                .GET("/fn/customers.php", r -> {
                    final var map = Map.of(
                        "customers", repository.findAll(),
                            "type", "Functional Reactive"
                    );

                    return ServerResponse.ok().render("customers", map);
                }).build();
    }

}
