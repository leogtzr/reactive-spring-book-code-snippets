package com.playground;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

/*
This shows how to use the publish operator to create a Publisher that lets you "pile on" subscribers
until a limit is reached. Then, all subscribers may observe the results.
 */
public class HotStreamTest3 {

    private List<Integer> one = new ArrayList<Integer>();
    private List<Integer> two = new ArrayList<Integer>();
    private List<Integer> three = new ArrayList<Integer>();

    @Test
    public void publish() {
        final Flux<Integer> pileOn = Flux.just(1, 2, 3)
                .publish()
                .autoConnect(3)
                .subscribeOn(Schedulers.immediate())
                ;

        pileOn.subscribe(one::add);
        Assertions.assertEquals(0, this.one.size());

        pileOn.subscribe(two::add);
        Assertions.assertEquals(0, this.two.size());

        pileOn.subscribe(three::add);
        Assertions.assertEquals(3, this.three.size());
        Assertions.assertEquals(3, this.two.size());
        Assertions.assertEquals(3, this.one.size());
    }

}
