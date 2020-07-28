package com.reactive.orders.service;

import com.reactive.orders.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ReactiveMongoTemplate template;
    private final TransactionalOperator operator;

    public Flux<Order> createOrders(final String... productIDs) {
        return this.operator.execute(status -> buildOrderFlux(template::insert, productIDs));
    }

    private Flux<Order> buildOrderFlux(final Function<Order, Mono<Order>> callback, final String[] productIDs) {
        return Flux.just(productIDs)
                .map(pid -> {
                    Assert.notNull(pid, "the producet ID shouldn't be null");
                    return pid;
                })
                .map(x -> new Order(null, x))
                .flatMap(callback)
                ;
    }

}
