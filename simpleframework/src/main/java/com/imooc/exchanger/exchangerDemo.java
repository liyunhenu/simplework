package com.imooc.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class exchangerDemo {


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Exchanger<String> exchanger = new Exchanger();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String myword = new String("我出石头！！！");
                try {
                    String result=exchanger.exchange(myword);
                    System.out.println("我是石头哥+++"+result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String myword = new String("我出剪刀！！！");
                try {
                    Thread.sleep(3*1000);
                    String result=exchanger.exchange(myword);
                    System.out.println("我是剪刀哥+++"+result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
