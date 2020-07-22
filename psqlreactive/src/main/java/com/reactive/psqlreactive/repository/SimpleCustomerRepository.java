package com.reactive.psqlreactive.repository;

import com.reactive.psqlreactive.domain.Customer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SimpleCustomerRepository {

    Mono<Customer> save(Customer c);
    Flux<Customer> findAll();
    Mono<Customer> update(Customer c);
    Mono<Customer> findById(Integer id);
    Mono<Void> deleteById(Integer id);

}
