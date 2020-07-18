package com.playground;

import org.junit.jupiter.api.Test;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class ControlFlowRetryTest {

    @Test
    public void retry() {
        final var errored = new AtomicBoolean();
        // can we make retry() work? how do we demo it?
        final Flux<String> producer = Flux.create(sink -> {
            if (!errored.get()) {
                errored.set(true);
                sink.error(new RuntimeException("Nope!"));
                log.info("returning a " + RuntimeException.class.getName() + "!");
            } else {
                log.info("we've already errored so here's the value");
                sink.next("hello");
            }
            sink.complete();
        });

        final Flux<String> retryOnError = producer.retry();
        StepVerifier.create(retryOnError).expectNext("hello").verifyComplete();
    }

}
