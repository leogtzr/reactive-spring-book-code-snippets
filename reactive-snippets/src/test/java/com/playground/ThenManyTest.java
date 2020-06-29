package com.playground;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;

public class ThenManyTest {

    @Test
    public void thenMany() {
        final var letters = new AtomicInteger();
        final var numbers = new AtomicInteger();

        final Flux<String> lettersPublisher = Flux.just("a", "b", "c")
                .doOnNext(_v -> letters.incrementAndGet());
        final Flux<Integer> numbersPublisher = Flux.just(1, 2, 3)
                .doOnNext(_n -> numbers.incrementAndGet());
        final Flux<Integer> thisBeforeThat = lettersPublisher.thenMany(numbersPublisher);
        StepVerifier.create(thisBeforeThat).expectNext(1, 2, 3).verifyComplete();

        Assertions.assertEquals(letters.get(), 3);
        Assertions.assertEquals(numbers.get(), 3);
    }

}
