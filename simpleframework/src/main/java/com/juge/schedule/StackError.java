package com.juge.schedule;

import java.util.LinkedList;

public class StackError {

    LinkedList list = new LinkedList();


    public synchronized void push(Object x) {
        synchronized (list) {
            list.addLast(x);
            notify();
        }
    }

    public synchronized Object pop()
            throws Exception {
        synchronized (list) {
            if (list.size() <= 0) {
                wait();
            }
            return list.removeLast();
        }
    }

    public static void main(String[] args) throws Exception {



        StackError stackError =new StackError();

        System.out.println("start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    stackError.pop();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }).start();
        System.out.println("do some thing");
        Thread.sleep(1000);
        stackError.push(9);
        System.out.println("over");


        /**
         * 笔试题目，上面的pop和push方法，至少有两个问题
         * 1. push和pop方法里，有两个锁，分别锁的是stack实例对象和linkedList实例对象，而pop方法里，
         * wait()方法释放的是linkedList实例对象的锁，外层stack实例对象对象的锁并没有释放，所以先执行了pop方法的时候，后执行push方法，
         * pop方法执行到的对象，加了两个锁，wait()只释放了一个linkedList实例对象的锁的锁，stack的锁并没有释放。另外一个线程无法走完push方法拿到锁
         * 造成死锁。
         * 2.假设所有方法都是先push再pop的,notify()方法唤醒一个线程后，pop方法前的条件检测，应该是用while循环，防止
         */
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("push"+finalI);
                    stackError.push(finalI);
                }
            }).start();

        }
        for (int i = 0; i < 6; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(stackError.pop());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }).start();
        }

    }
}
