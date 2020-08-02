package com.customer.utils;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import com.customer.utils.CountAndString;

public abstract class IntervalMessageProducer {

    public static Flux<String> produce(final int c) {
        return produce().take(c);
    }

    public static Flux<String> produce() {
        return doProduceCountAndStrings().map(CountAndString::getMessage);
    }

    private static Flux<CountAndString> doProduceCountAndStrings() {
        final var counter = new AtomicLong();
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> new CountAndString(counter.incrementAndGet()))
                ;
    }

}
