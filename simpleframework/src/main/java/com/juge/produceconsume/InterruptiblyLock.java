package com.juge.produceconsume;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock锁是可中断的，可轮询的，定时锁
 * lock1.lockInterruptibly()上锁
 * tryLock方法可以加参数，实现定时锁
 * ReentrantLock可以设置公平锁和非公平锁，在构造函数中加boolean fail参数，默认是非公平锁
 */
public class InterruptiblyLock {
    public ReentrantLock lock1=new ReentrantLock();//第一把锁1
    public ReentrantLock lock2=new ReentrantLock();//第二把锁lock2
    public Thread lock1(){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.lockInterruptibly();//如果当前线程未被中断，则获取锁
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock2.lockInterruptibly();
                    System.out.println(Thread.currentThread().getName()+"执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (lock1.isHeldByCurrentThread()){
                        lock1.unlock();
                    }
                    if (lock2.isHeldByCurrentThread()){
                        lock2.unlock();
                    }
                    System.out.println(Thread.currentThread().getName()+"退出");
                }
            }
        });
        t.start();
        return t;
    }

    public Thread lock2(){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.lockInterruptibly();
                    try{
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock1.lockInterruptibly();
                    System.out.println(Thread.currentThread().getName()+"执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (lock1.isHeldByCurrentThread()){
                        lock1.unlock();
                    }
                    if (lock2.isHeldByCurrentThread()){
                        lock2.unlock();
                    }
                    System.out.println(Thread.currentThread().getName()+"退出");
                }
            }
        });
        t.start();
        return t;
    }

    public static void main(String[] args) throws InterruptedException{
        long time=System.currentTimeMillis();
        InterruptiblyLock interruptiblyLock=new InterruptiblyLock();
        Thread thread1=interruptiblyLock.lock1();
        //Thread.sleep(1000);
        Thread thread2=interruptiblyLock.lock2();
        //自旋一段时间，如果等待时间过长，则可能发生了死锁等待问题，主动中断并释放锁。
        while (true){
            if (System.currentTimeMillis()-time>=300){
                //线程2退出，让出锁，1拿到锁执行。
                thread2.interrupt();//中断线程1
            }
        }
    }
}

