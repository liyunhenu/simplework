package com.juge.produceconsume;

import com.juge.produceconsume.Main;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main2{



    private AtomicInteger atomicInteger=new AtomicInteger(0);//原子整数

    private final Queue queue=new LinkedList<>();//

    private final ReentrantLock lock=new ReentrantLock();

    private final Condition FULL_CONDITION=lock.newCondition();

    private final Condition EMPTY_CONDITION=lock.newCondition();

    private final int cap;

    public Main2(int capacity){
        this.cap=capacity;
    }


    public Runnable produce(){
        return new Runnable(){
            @Override
            public void run(){
                try {
                    lock.lockInterruptibly();
                    while (queue.size()>cap){
                        FULL_CONDITION.await();
                    }
                    Integer integer=atomicInteger.getAndIncrement();
                    System.out.println("produce:"+integer);
                    queue.offer(integer);
                    EMPTY_CONDITION.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };
    }


    public Runnable consume(){
        return  new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lockInterruptibly();
                    while(queue.size()<=0){
                        EMPTY_CONDITION.await();
                    }
                    System.out.println("consume:++++"+queue.poll());
                    FULL_CONDITION.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        };
    }


    public static void main(String[] args){

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(15, 15, 10000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new CustomizableThreadFactory("myThread"), new ThreadPoolExecutor.AbortPolicy());
        Main2 main=new Main2(20);
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(main.consume());
        }
        //Thread.sleep(1000);
        System.out.println("消费线程已经等待就绪");
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(main.produce());

        }

        //Thread.sleep(5000);
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()){

        }

        //BlockingQueue blockingQueue=new LinkedBlockingQueue<>();

        ExecutorService executor =Executors.newFixedThreadPool(2);



    }
}