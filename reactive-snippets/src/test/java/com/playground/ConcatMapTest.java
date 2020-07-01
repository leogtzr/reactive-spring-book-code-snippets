package com.playground;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ConcatMapTest {

    @Test
    public void concatMap() {
        final Flux<Integer> data = Flux.just(new Pair(1, 300), new Pair(2, 200), new Pair(3, 300))
                .concatMap(p -> this.delayReplyFor(p))
                ;
        StepVerifier
                .create(data)
                .expectNext(1, 2, 3)            /// In order ...
        .verifyComplete();
    }

    private Flux<Integer> delayReplyFor(final Pair pair) {
        return Flux.just(pair.id).delayElements(Duration.ofMillis(pair.delay));
    }

    @AllArgsConstructor
    private static final class Pair {
        private int id;
        private long delay;
    }


}
