package com.customer.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@Log4j2
public class CustomerClient {

    private final WebClient webClient;

    private String base = "localhost:8080";

    public CustomerClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    public void setBase(final String base) {
        this.base = base;
        log.info("setting base to: " + base);
    }

    public Flux<Customer> getAllCustomers() {
        return this.webClient
                .get()
                .uri("http://" + this.base + "/customers")
                .retrieve()
                .bodyToFlux(Customer.class)
                ;
    }
}
