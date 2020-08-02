package com.customer.routes;

import com.customer.domain.Customer;
import com.customer.repository.CustomerRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CustomerHandler {

    private final CustomerRepository repository;

    public CustomerHandler(final CustomerRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> handleFindAll(final ServerRequest req) {
        final var all = this.repository.findAll();
        return ok().body(all, Customer.class);
    }

    public Mono<ServerResponse> handlerFindCustomerById(final ServerRequest req) {
        final var id = req.pathVariable("id");
        final var byId = this.repository.findById(id);
        return ok().body(byId, Customer.class);
    }

    public Mono<ServerResponse> handleCreateCustomer(final ServerRequest req) {
        return req.bodyToMono(Customer.class)
                .flatMap(this.repository::save)
                .flatMap(saved -> created(URI.create("/fn/customers/" + saved.getId())).build());
    }

}
