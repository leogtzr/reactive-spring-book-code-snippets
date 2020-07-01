package com.playground;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FlatMapTest {
    @Test
    public void flatMap() {
        final Flux<Integer> data = Flux.just(new Pair(1, 300), new Pair(2, 200), new Pair(3, 300))
                .flatMap(pair -> this.delayReplayFor(pair.id, pair.delay));
        StepVerifier.create(data).expectNext(3, 2, 1).verifyComplete();
    }

    private Flux<Integer> delayReplayFor(final int id, final long delay) {
        return Flux.just(id).delayElements(Duration.ofMillis(delay));
    }

    @AllArgsConstructor @Data
    private static class Pair {
        private int id;
        private long delay;
    }

}
