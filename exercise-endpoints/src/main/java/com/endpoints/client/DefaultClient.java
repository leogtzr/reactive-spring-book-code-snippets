package com.endpoints.client;

import com.endpoints.domain.Greeting;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Log4j2
public class DefaultClient {

    private final WebClient client;

    public DefaultClient(final WebClient client) {
        this.client = client;
    }

    public Mono<Greeting> getSingle(final String name) {
        return client.get()
                .uri("/greet/single/{name}", Map.of("name", name))
                .retrieve()
                .bodyToMono(Greeting.class)
                ;
    }

    public Flux<Greeting> getMany(final String name) {
        return client
                .get()
                .uri("/greet/many/{name}", Map.of("name", name))
                .retrieve()
                .bodyToFlux(Greeting.class)
                .take(10)
                ;
    }

}
