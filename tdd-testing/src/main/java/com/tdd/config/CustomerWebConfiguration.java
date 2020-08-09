package com.tdd.config;

import com.tdd.domain.Customer;
import com.tdd.repository.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class CustomerWebConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routes(final CustomerRepository customerRepository) {
        return route(GET("/customers"), req -> ok().body(customerRepository.findAll(), Customer.class));
    }

}
