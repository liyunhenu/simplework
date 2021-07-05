package com.juge.produceconsume;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executor=new ThreadPoolExecutor(20,20,100, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(10),new CustomizableThreadFactory("myThread"),new ThreadPoolExecutor.AbortPolicy());
        Semaphore semaphore=new Semaphore(5);
         AtomicInteger integer=new AtomicInteger(0);
        for (int i = 0; i <15 ; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("测试令牌+"+integer.addAndGet(1));
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    semaphore.release();
                }
            });
        }
        executor.shutdown();

        while (!executor.isTerminated()){

        }

    }
}
