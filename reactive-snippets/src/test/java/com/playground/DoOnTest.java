package com.playground;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;

@Log4j2
public class DoOnTest {

    @Test
    public void doOn() {
        final var signals = new ArrayList<Signal<Integer>>();
        final var nextValues = new ArrayList<Integer>();
        final var subscriptions = new ArrayList<Subscription>();
        final var exceptions = new ArrayList<Throwable>();
        final var finallySignals = new ArrayList<SignalType>();

        final Flux<Integer> on = Flux//
            .<Integer>create(sink -> {
                sink.next(1);
                sink.next(2);
                sink.next(3);
                sink.error(new IllegalArgumentException("oops!"));
                sink.complete();
            })
                .doOnNext(nextValues::add)      //
                .doOnEach(signals::add)         //
                .doOnSubscribe(subscriptions::add)//
                .doOnError(IllegalArgumentException.class, exceptions::add)
                .doFinally(finallySignals::add)
                ;

        StepVerifier.
                create(on)
                .expectNext(1, 2, 3)
                .expectError(IllegalArgumentException.class)
                .verify()
                ;

        signals.forEach(log::info);
        Assertions.assertEquals(4, signals.size());

        signals.forEach(log::info);
        Assertions.assertEquals(4, signals.size());

        finallySignals.forEach(log::info);
        Assertions.assertEquals(finallySignals.size(), 1);

        subscriptions.forEach(log::info);
        Assertions.assertEquals(subscriptions.size(), 1);

        exceptions.forEach(log::info);
        Assertions.assertEquals(exceptions.size(), 1);
        Assertions.assertTrue(exceptions.get(0) instanceof IllegalArgumentException);

        nextValues.forEach(log::info);
        Assertions.assertEquals(Arrays.asList(1, 2, 3), nextValues);


    }

}
