package com.playground;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    @Test
    public void foo() {

        final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(5);

        final Thread run1 = new Thread(() -> {
            System.out.println("Hello from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
                COUNT_DOWN_LATCH.countDown();
            } catch (final InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        final Thread run2 = new Thread(() -> {
            System.out.println("Hello from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
                COUNT_DOWN_LATCH.countDown();
            } catch (final InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        final Thread run3 = new Thread(() -> {
            System.out.println("Hello from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
                COUNT_DOWN_LATCH.countDown();
            } catch (final InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        final Thread run4 = new Thread(() -> {
            System.out.println("Hello from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
                COUNT_DOWN_LATCH.countDown();
            } catch (final InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        final Thread run5 = new Thread(() -> {
            System.out.println("Hello from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
                COUNT_DOWN_LATCH.countDown();
            } catch (final InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        run1.start();
        run2.start();
        run3.start();
        run4.start();
        run5.start();

        try {
            //System.out.println(Thread.currentThread().getName());
            System.out.printf("I am waiting here: [%s]\n", Thread.currentThread().getName());
            COUNT_DOWN_LATCH.await();
            System.out.println("I am done ... ");
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
