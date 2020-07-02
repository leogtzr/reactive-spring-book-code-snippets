package com.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FilterTest {

    @Test
    public void filter() {
        final Flux<Integer> range = Flux.range(1, 1000).take(5);
        final Flux<Integer> filter = range.filter(n -> n % 2 != 0);
        StepVerifier.create(filter).expectNext(1, 3, 5).verifyComplete();
    }

}
