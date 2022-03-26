package com.juge.produceconsume;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {


    private  final Object BUFFER_OBJECT = new Object();//锁对象，使用对象的内置队列

    private final Queue<Integer> list = new LinkedList<>();

    private final AtomicInteger auto = new AtomicInteger();


    public Runnable newProduceTask(){
        return new Runnable() {
            @Override
            public void run() {
              produce();
            }
        };
    }

    public Runnable newConsumeTask(){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private   void  produce() {

        synchronized (BUFFER_OBJECT) {
            //
            Integer input = auto.getAndIncrement();
            System.out.println("produce: " + input);
            list.offer(input);
            BUFFER_OBJECT.notify();
        }

    }


    public  void consume() throws InterruptedException {
        synchronized(BUFFER_OBJECT) {
            while (list.size() <= 0) {
                System.out.println( Thread.currentThread()+"biu");
                BUFFER_OBJECT.wait();
            }
            //do st
            //Integer p = list.poll();
            System.out.println("consume+++ " + list.poll());
        }

    }


    public static void main(String[] arg) throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(15, 15, 10000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new CustomizableThreadFactory("myThread"), new ThreadPoolExecutor.AbortPolicy());
        Main main=new Main();
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(main.newConsumeTask());
        }
        Thread.sleep(1000);
        System.out.println("消费线程已经等待就绪");
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(main.newProduceTask());
        }

        //Thread.sleep(5000);
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()){

        }


        final Stack stack=new Stack();
        AtomicInteger integer=new AtomicInteger(0);
        /*System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push(1);
        stack.push(2);
        stack.push(3);*/

        /*for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //System.out.println();
                        System.out.println("pop:"+stack.pop());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("消费线程已经等待就绪");
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Integer input=integer.getAndIncrement();
                    System.out.println("push:"+input);
                    stack.push(input);
                }
            }).start();

        }

        Thread.sleep(5000);
        Thread.sleep(1000);*/

    }

}
