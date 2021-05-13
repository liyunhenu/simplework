package com.imooc.semaphore;

import java.util.concurrent.*;

public class SemaPhoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(5);//五个线程可以同时访问
        ExecutorService executor=new ThreadPoolExecutor(5,30,1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i <20 ; i++) {
            final int INDEX=i;
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        Thread.sleep((long)1000);

                        System.out.println("Accessing+++"+INDEX);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            executor.execute(runnable);
        }
        executor.shutdown();
    }
}
