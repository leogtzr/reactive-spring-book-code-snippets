package com.endpoints.controller;

import com.endpoints.ExerciseEndpointsApplication;
import com.endpoints.client.AuthenticatedClient;
import com.endpoints.client.DefaultClient;
import com.endpoints.config.AuthenticatedConfiguration;
import com.endpoints.config.ClientProperties;
import com.endpoints.config.DefaultConfiguration;
import com.endpoints.domain.Greeting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = { AuthenticatedConfiguration.class, DefaultConfiguration.class,
        ClientProperties.class, ExerciseEndpointsApplication.class }, //
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
        properties = { "spring.profiles.active=client", "server.port=8080",
                "spring.main.web-application-type=reactive" })
public class HttpControllerTest {

    @Autowired
    private AuthenticatedClient authenticatedClient;

    @Autowired
    private DefaultClient defaultClient;

    @Test
    public void greetSingle() {
        final Mono<Greeting> helloMono = this.defaultClient.getSingle("Leo");
        StepVerifier.create(helloMono)
                .expectNextMatches(g -> g.getMessage().contains("Hello Leo"))
                .verifyComplete();
    }

    @Test
    public void greetMany() {
        final Flux<Greeting> helloFlux = this.defaultClient.getMany("Stephane").take(2);
        String msg = "Hello Stephane";
        StepVerifier.create(helloFlux)
                .expectNextMatches(g -> g.getMessage().contains(msg))
                .expectNextMatches(g -> g.getMessage().contains(msg)).verifyComplete();
    }

    @Test
    public void greetAuthenticated() {
        Mono<Greeting> authenticatedGreeting = this.authenticatedClient
                .getAuthenticatedGreeting();
        StepVerifier.create(authenticatedGreeting)
                .expectNextMatches(g -> g.getMessage().contains("Hello leo"))
                .verifyComplete();
    }

}
