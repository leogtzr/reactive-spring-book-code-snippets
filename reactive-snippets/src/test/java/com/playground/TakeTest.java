package com.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TakeTest {

    @Test
    public void take() {
        final var count = 10;
        // We only want 10 (count)
        final Flux<Integer> take = range().take(count);
        StepVerifier.create(take).expectNextCount(count).verifyComplete();
    }

    @Test
    public void takeUntil() {
        final var count = 50;
        final Flux<Integer> take = range().takeUntil(n -> n == count);
        StepVerifier.create(take).expectNextCount(count).verifyComplete();
    }

    private Flux<Integer> range() {
        return Flux.range(1, 10_000);
    }
}