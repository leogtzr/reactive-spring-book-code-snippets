package com.reactive.orders.service;

import com.reactive.orders.config.TransactionConfiguration;
import com.reactive.orders.domain.Order;
import com.reactive.orders.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Log4j2
@Import({TransactionConfiguration.class, OrderService.class})
class OrderServiceTest {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService service;

    @Autowired
    private ReactiveMongoTemplate template;

    @BeforeAll
    public static void warn() throws IOException  {
        final Resource script = new FileSystemResource("setup-mongo.sh");
        log.info(script.getFile().getAbsoluteFile());
        Assertions.assertThat(script.exists()).isTrue();
        final Charset charset = Charset.defaultCharset();
        final String instructions = StreamUtils.copyToString(script.getInputStream(), charset);
        log.warn("Be sure MongoDB supports replicas. Try:\n\n" + instructions);
    }

    @BeforeEach
    public void configureCollectionsBeforeTests() {
        Mono<Boolean> createIfMissing = template.collectionExists(Order.class) //
                .filter(x -> !x) //
                .flatMap(exists -> template.createCollection(Order.class)) //
                .thenReturn(true);
        StepVerifier //
                .create(createIfMissing) //
                .expectNextCount(1) //
                .verifyComplete();
    }

    @Test
    public void createOrders() {
        final Publisher<Order> orders = this.repository
                .deleteAll()
                .thenMany(this.service.createOrders("1", "2", "3"))
                .thenMany(this.repository.findAll());

        StepVerifier
                .create(orders)
                .expectNextCount(3)
                .verifyComplete()
        ;
    }

    @Test
    public void executeRollback() {
        this.runTransactionalTest(this.service.createOrders("1", "2", null));
    }

    @Test
    public void transactionalOperatorRollback() {
        // This test demonstrates that writing three records, one of which is null, results in a rollback with no
        //observable side effects.
        this.runTransactionalTest(this.service.createOrders("1", "2", null));
    }

    @Test
    public void transactionalRollback() {
        this.runTransactionalTest(this.service.createOrders("1", "2", null));
    }

    private void runTransactionalTest(Flux<Order> ordersInTx) {
        Publisher<Order> orders = this.repository //
                .deleteAll() //
                .thenMany(ordersInTx) //
                .thenMany(this.repository.findAll());
        StepVerifier //
                .create(orders) //
                .expectNextCount(0) //
                .verifyError();
        StepVerifier //
                .create(this.repository.findAll()) //
                .expectNextCount(0) //
                .verifyComplete();
    }

}