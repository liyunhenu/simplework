package com.juge.produceconsume;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main1 {


    private BlockingQueue queue = new LinkedBlockingQueue(3);

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public Runnable produce() {
        return new ProduceRunnable();
    }

    public Runnable consume() {
        return new ConsumeRunnable();
    }


    public class ProduceRunnable implements Runnable {
        @Override
        public void run() {
            Integer input = atomicInteger.getAndIncrement();

            try {
                System.out.println("produce: " + input);
                queue.put(input);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class ConsumeRunnable implements Runnable {
        @Override
        public void run() {
            Integer output = null;
            try {

                System.out.println("consume+++ " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public static void main(String[] args) throws InterruptedException {
        Main1 mainObject = new Main1();
        //ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 20, 1000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new CustomizableThreadFactory("my"), new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 20; i++) {
            new Thread(mainObject.consume()).start();
        }
        //Thread.sleep(1000);
        for (int i = 0; i < 20; i++) {
            new Thread(mainObject.produce()).start();
        }

        /*for (int i = 0; i < 5; i++) {
            pool.submit(mainObject.consume());

        }
        Thread.sleep(1000);
        for (int i = 0; i < 7; i++) {
            pool.submit(mainObject.produce());
        }
        pool.shutdown();
        while (!pool.isTerminated()) {

        }*/

    }
}
