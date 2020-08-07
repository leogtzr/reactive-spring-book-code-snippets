package com.endpoints.client;

import com.endpoints.domain.Greeting;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
public class AuthenticatedClient {

    private final WebClient client;

    public AuthenticatedClient(final WebClient client) {
        this.client = client;
    }

    public Mono<Greeting> getAuthenticatedGreeting() {
        return this.client
                .get()
                .uri("/greet/authenticated")
                .retrieve()
                .bodyToMono(Greeting.class)
                ;
    }

}
