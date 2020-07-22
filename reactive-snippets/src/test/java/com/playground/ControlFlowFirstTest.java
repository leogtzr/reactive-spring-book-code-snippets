package com.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ControlFlowFirstTest {

    @Test
    public void first() {
        final Flux<Integer> slow = Flux.just(1, 2, 3)
                .delayElements(Duration.ofMillis(10))
                ;
        // This stream is generated every 2 milliseconds so is faster than the "slow" one ...
        final Flux<Integer> fast = Flux.just(4, 5, 6, 7)
                .delayElements(Duration.ofMillis(2));
        final Flux<Integer> first = Flux.first(slow, fast);
        StepVerifier.create(first).expectNext(4, 5, 6, 7).verifyComplete();
    }

}
