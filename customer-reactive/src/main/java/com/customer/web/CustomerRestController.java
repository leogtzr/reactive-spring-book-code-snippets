package com.customer.web;

import com.customer.domain.Customer;
import com.customer.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping(value = "/rc/customers")
public class CustomerRestController {

    private final CustomerRepository repository;

    public CustomerRestController(final CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Mono<Customer> byId(@PathVariable final String id) {
        return this.repository.findById(id);
    }

    @GetMapping
    public Flux<Customer> all() {
        return this.repository.findAll();
    }

    @PostMapping
    public Mono<ResponseEntity<?>> create(@RequestBody final Customer customer) {
        return this.repository.save(customer)//
                .map(customerEntity -> ResponseEntity//
                        .created(URI.create("/rc/customers/" + customerEntity.getId()))
                        .build());
    }
}
