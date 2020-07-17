package com.playground;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HotStreamTest1 {

    // The first subscriber sees all the elements since it subscribed at the outset.

    @Test
    public void hot() throws Exception {
        var first = new ArrayList<Integer>();
        var second = new ArrayList<Integer>();

        final EmitterProcessor<Integer> emitter = EmitterProcessor.create(2);
        final FluxSink<Integer> sink = emitter.sink();

        // emitter.subscribe(first::add);
        emitter.subscribe(collect(first));
        sink.next(1);
        sink.next(2);

        // emitter.subscribe(second::add);
        emitter.subscribe(collect(second));
        sink.next(3);
        sink.complete();

        Assertions.assertTrue(first.size() > second.size());
    }

    private Consumer<Integer> collect(final List<Integer> collection) {
        return collection::add;
    }

}
