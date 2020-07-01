package com.playground;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class SwitchMapTest {

    @Test
    public void switchMapWithLookaheads() {
        final Flux<String> source = Flux
                .just("re", "rea", "reac", "reac", "reactive")
                .delayElements(Duration.ofMillis(100))
                .switchMap(this::lookup)
                ;
        StepVerifier.create(source)
                .expectNext("reactive -> reactive")
                .verifyComplete();
    }

    private Flux<String> lookup(final String word) {
        return Flux.just(word + " -> reactive")
                .delayElements(Duration.ofMillis(500))
                ;
    }

}