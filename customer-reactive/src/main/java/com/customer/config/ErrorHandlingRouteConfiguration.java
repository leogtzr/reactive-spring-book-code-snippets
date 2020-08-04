package com.customer.config;

import com.customer.domain.Product;
import com.customer.exception.ProductNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

@Configuration
public class ErrorHandlingRouteConfiguration {

    @Bean
    public RouterFunction<ServerResponse> errors() {
        final var productIdPathVariable = "productId";
        return route()
                .GET("/products/{" + productIdPathVariable + "}", request -> {
                    final var productId = request.pathVariable(productIdPathVariable);
                    if (!Set.of("1", "2").contains(productId)) {
                        return ServerResponse.ok().syncBody(new Product(productId));
                    } else {
                        return Mono.error(new ProductNotFoundException(productId));
                    }
                })
                .filter((request, next) -> next.handle(request)
                        .onErrorResume(ProductNotFoundException.class, pnfe -> notFound().build())
                )
                .build();
    }

}
