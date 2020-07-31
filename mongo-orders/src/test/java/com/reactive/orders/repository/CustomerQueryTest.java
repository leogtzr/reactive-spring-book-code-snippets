package com.reactive.orders.repository;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.reactive.orders.domain.Customer;
import com.reactive.orders.domain.Order;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
// import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Log4j2
@DataMongoTest
public class CustomerQueryTest {

    @Autowired
    private ReactiveMongoTemplate operations;

    @Autowired
    private CustomerRepository repository;

    @BeforeAll
    public void before() {
        final CollectionOptions capped = CollectionOptions.empty().size(1024 * 1024)
                .maxDocuments(100).capped();
        final Mono<MongoCollection<Document>> recreateCollection = operations.collectionExists(Order.class)
                .flatMap(exists -> exists ? operations.dropCollection(Customer.class) : Mono.just(exists))
                .then(operations.createCollection(Customer.class, capped));
        StepVerifier.create(recreateCollection)
                .expectNextCount(1L)
                .verifyComplete()
                ;
    }

    @Test
    public void tail() throws InterruptedException {
        final Queue<Customer> people = new ConcurrentLinkedDeque<>();

        StepVerifier.create(this.write().then(this.write()))
                .expectNextCount(1)
                .verifyComplete();

        this.repository.findByName("1")
                .doOnNext(people::add)
                .doOnComplete(() -> log.info("complete"))
                .doOnTerminate(() -> log.info("terminated"))
                .subscribe()
                ;

        Assertions.assertThat(people).hasSize(2);

        StepVerifier.create(this.write().then(this.write()))
                .expectNextCount(1)
                .verifyComplete();

        Thread.sleep(1000);
        Assertions.assertThat(people).hasSize(4);

    }

    private Mono<Customer> write() {
        return repository.save(new Customer(UUID.randomUUID().toString(), "1"));
    }

}

/*
1 We explicitly create a capped collection
2 This test accumulates results from the capped collection and the tailable query into a Queue.
3 Write two records to the now pristine collection to the database.
147Reactive Spring
4 Run the tailable query which returns a Publisher<Customer>, to which we’ll subscribe. As new
records arrive, we capture them in the previously defined Queue.
5 Once subscribed, confirm that the first two records are in the collection, let’s write two more
records.
6 Confirm the updates to the Queue (without having to re-run the query.)
 */