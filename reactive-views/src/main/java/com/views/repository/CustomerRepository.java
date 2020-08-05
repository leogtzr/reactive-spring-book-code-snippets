package com.views.repository;

import com.views.domain.Customer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepository {

    private final Map<String, Customer> data = new ConcurrentHashMap<>();

    public Mono<Customer> findById(final String id) {
        return Mono.just(this.data.get(id));
    }

    public Mono<Customer> save(final Customer customer) {
        final var uuid = UUID.randomUUID().toString();
        this.data.put(uuid, new Customer(uuid, customer.getName()));
        return Mono.just(this.data.get(uuid));
    }

    public final Flux<Customer> findAll() {
        return Flux.fromIterable(this.data.values());
    }

}
