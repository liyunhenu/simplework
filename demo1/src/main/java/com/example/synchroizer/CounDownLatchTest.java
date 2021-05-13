package com.example.synchroizer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CounDownLatchTest {


    public static void main(String[] args) {
        Map context = new HashMap();
        Long start = System.currentTimeMillis();
        /*try {
            process1(context);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            process2(context);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        final Map context1 = context;
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process1(context1);
                    context1.put("Thead1", "1");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process2(context1);
                    context1.put("Thead2", "2");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        }).start();
        /*new Thread() {
            @Override
            public void run() {
                try {
                    process1(context1);
                    context1.put("Thead1", "1");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                super.run();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    process2(context1);
                    context1.put("Thead2", "2");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                super.run();
            }
        }.start();*/
        try {
            countDownLatch.await();
            System.out.println(context1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Long end = System.currentTimeMillis();
        Double consumeTime = Double.valueOf(end - start) / 1000;
        System.out.println("耗时" + consumeTime + "秒");


    }

    public static void process1(Map context) throws InterruptedException {
        Thread.sleep(3000);
    }

    public static void process2(Map context) throws InterruptedException {
        Thread.sleep(4000);
    }

}
