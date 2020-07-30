package com.reactive.orders.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;

@EnableTransactionManagement
@Configuration
public class TransactionConfiguration {

    @Bean
    public TransactionalOperator transactionalOperator(final ReactiveTransactionManager txm) {
        return TransactionalOperator.create(txm);
    }

    @Bean
    public ReactiveTransactionManager reactiveMongoTransactionManager(final ReactiveMongoDatabaseFactory rdf) {
        return new ReactiveMongoTransactionManager(rdf);
    }

}
