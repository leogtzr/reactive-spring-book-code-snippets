package com.playground;

// import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicBoolean;

// @SpringBootTest
public class TransformTest {

    @Test
    public void transform() {
        final var finished = new AtomicBoolean();
        final var letters = Flux.just("A", "B", "C")
                .transform(
                        stringFlux -> stringFlux.doFinally(signal -> finished.set(true))
                );

        //assertTrue("the finished Boolean must be true", finished.get());
        StepVerifier.create(letters).expectNextCount(3).verifyComplete();
        assertTrue(finished.get(), "the finished Boolean must be true");

    }

}
