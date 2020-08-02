package com.customer.config;

import com.customer.routes.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CustomerApiEndpointConfiguration {

    /*
    The registrations defined here behave identically in functionality to those in CustomerRestController,
except they start with /fn, not /rc. The logic for each endpoint lives in a handler object,
CustomerHandler.
     */
    @Bean
    public RouterFunction<ServerResponse> customerApis(final CustomerHandler handler) {
        return route()
                .nest(path("/fn/customers"), builder -> builder
                .GET("/{id}", handler::handlerFindCustomerById)
                .GET("", handler::handleFindAll)
                .POST("", handler::handleCreateCustomer))
                .build()
                ;
    }

}
