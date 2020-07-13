package com.playground;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class SchedulersHookTest {

    @Test
    public void onScheduleHook() {
        final var counter = new AtomicInteger();
        Schedulers.onScheduleHook("my hook", runnable -> () -> {
            final String threadName = Thread.currentThread().getName();
            counter.incrementAndGet();
            log.info("before execution: " + threadName);
            runnable.run();
            log.info("after execution: " + threadName);
        });
        // immediate(): to immediately run submitted Runnable instead of scheduling them (somewhat of a no-op or "null object" Scheduler)
        // ref: https://projectreactor.io/docs/core/release/api/reactor/core/scheduler/Schedulers.html
        final Flux<Integer> integerFlux = Flux.just(1, 2, 3).delayElements(Duration.ofMillis(1)).subscribeOn(Schedulers.immediate());
        StepVerifier.create(integerFlux).expectNext(1, 2, 3).verifyComplete();
        Assertions.assertEquals(3, counter.get(), "count should be 3");
    }

}
