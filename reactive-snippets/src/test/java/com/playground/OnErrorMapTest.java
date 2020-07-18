package com.playground;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;

/*
Use onErrorMap if you want to normalize errors or, for some reason, map one error to another. You can
use it with other operators to filter particular errors, then canonicalize them, then route to a shared
error handler.
 */
public class OnErrorMapTest {

    @Test
    public void onErrorMap() throws Exception {
        final class GenericException extends RuntimeException {
            GenericException(final String msg) {
                super(msg);
            }
            GenericException() {}
        }

        final var counter = new AtomicInteger();
        final Flux<Integer> resultsInError = Flux.error(new IllegalArgumentException("Oops!"));
        final Flux<Integer> errorHandlingStream = resultsInError
                // Map the error we got earlier (IllegalArgumentException) to another exception (GenericException)
                .onErrorMap(IllegalArgumentException.class, ex -> new GenericException(ex.getMessage()))
                .doOnError(GenericException.class, ge -> counter.incrementAndGet());
        StepVerifier.create(errorHandlingStream).expectError().verify();
        Assertions.assertEquals(1, counter.get());
    }

}