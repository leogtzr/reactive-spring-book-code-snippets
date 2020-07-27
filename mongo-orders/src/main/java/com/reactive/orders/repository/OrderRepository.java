package com.reactive.orders.repository;

import com.reactive.orders.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<Order, String> {
    Flux<Order> findByProductId(String productId);
}
