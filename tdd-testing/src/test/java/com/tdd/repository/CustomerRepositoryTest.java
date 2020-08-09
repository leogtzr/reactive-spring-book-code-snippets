package com.tdd.repository;

import com.tdd.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@RunWith(SpringRunner.class)
/*
the @DataMongoTest is the relevant test-slice for working with Spring Data and MongoDB in particular.
 */
@DataMongoTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findByName() {
        final String commonName = "Jane";
        final Customer one = new Customer("1", commonName);
        final Customer two = new Customer("2", "John");
        final Customer three = new Customer("3", commonName);
        final Publisher<Customer> setup = this.customerRepository //
                .deleteAll() //
                .thenMany(this.customerRepository.saveAll(Flux.just(one, two, three))) //
                .thenMany(this.customerRepository.findByName(commonName));
        final Predicate<Customer> customerPredicate = customer ->  commonName.equalsIgnoreCase(customer.getName());
        StepVerifier
                .create(setup) //
                .expectNextMatches(customerPredicate) //
                .expectNextMatches(customerPredicate) //
                .verifyComplete();
    }

}
