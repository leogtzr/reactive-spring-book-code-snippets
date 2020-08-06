package com.endpoints.controller;

import com.endpoints.domain.Greeting;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
public class HttpController {

    // 1
    @GetMapping("/greet/single/{name}")                     // <--- application/json
    public Publisher<Greeting> greetSingle(@PathVariable final String name) {
        return Mono.just(greeting(name));
    }

    // 2
    @GetMapping(value = "/greet/many/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)     // <- text/event-stream
    public Publisher<Greeting> greetMany(@PathVariable final String name) {
        return Flux.
                fromStream(Stream.generate(() -> greeting(name)))
                .delayElements(Duration.ofSeconds(1))
                ;
    }

    // 3
    @GetMapping("/greet/authenticated")
    public Publisher<Greeting> greetAuthenticated(final Authentication authentication) {
        return Mono.just(greeting(authentication.getName()));
    }

    private Greeting greeting(final String name) {
        return new Greeting("Hello " + name + " @ " + Instant.now());
    }


}
