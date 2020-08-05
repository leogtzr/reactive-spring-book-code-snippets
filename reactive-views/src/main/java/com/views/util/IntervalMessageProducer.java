package com.views.util;

import com.views.domain.CountAndString;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

public abstract class IntervalMessageProducer {

    public static Flux<String> produce(final int c) {
        return produce().take(c);
    }

    public static Flux<String> produce() {
        return doProduceCountAndStrings().map(CountAndString::getMessage);
    }

    private static Flux<CountAndString> doProduceCountAndStrings() {
        final var counter = new AtomicLong();

        /*
            This endpoint produces new CountAndString values every second using the Flux.interval operator.
         */
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> new CountAndString(counter.incrementAndGet()))
                ;
    }

}
