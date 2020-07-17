package com.playground;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/*
This example is a little more involved since it forces convergence of both asynchronous subscribers
with a CountDownLatch and then evaluates whether the first stash of observed elements from the first
subscriber is more massive than the second stash of items from the second subscriber.
 */
@Log4j2
public class HotStreamTest2 {

    @Test
    public void hot() throws Exception {
        int factor = 10;
        log.info("Start");
        var cdl = new CountDownLatch(2);
        final Flux<Integer> live = Flux.range(0, 10).delayElements(Duration.ofMillis(factor))
                .share();
        var one = new ArrayList<Integer>();
        var two = new ArrayList<Integer>();
        live.doFinally(signalTypeConsumer(cdl)).subscribe(collect(one));
        Thread.sleep(factor * 2);
        live.doFinally(signalTypeConsumer(cdl)).subscribe(collect(two));
        cdl.await(5, TimeUnit.SECONDS);
        Assertions.assertTrue(one.size() > two.size());
        log.info("stop");
    }

    private Consumer<SignalType> signalTypeConsumer(final CountDownLatch cdl) {
        return signal -> {
            if (signal.equals(SignalType.ON_COMPLETE)) {
                try {
                    cdl.countDown();
                } catch (final Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    private Consumer<Integer> collect(final List<Integer> collection) {
        return collection::add;
    }

}
