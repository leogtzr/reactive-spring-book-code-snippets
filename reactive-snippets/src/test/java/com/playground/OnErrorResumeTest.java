package com.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class OnErrorResumeTest {

    private final Flux<Integer> resultsInError = Flux.just(1, 2, 3).flatMap(counter -> {
        if (counter == 2) {
            return Flux.error(new IllegalArgumentException("Oops!"));
        } else {
            return Flux.just(counter);
        }
    });

    @Test
    public void onErrorResume() {
        // resultsInError .... When hitting 2 an exception is thrown ...
        // With the following we generate a stream when there is an error, 1, 3, 2, 1
        final Flux<Integer> integerFlux = resultsInError
                .onErrorResume(IllegalArgumentException.class, e -> Flux.just(3, 2, 1));
        StepVerifier.create(integerFlux).expectNext(1, 3, 2, 1).verifyComplete();
    }

}
