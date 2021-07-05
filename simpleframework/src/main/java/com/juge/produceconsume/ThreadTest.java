package com.juge.produceconsume;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    private static ReentrantLock lock = new ReentrantLock(true);

    public static class T extends Thread
    {
        public T(String name) {
            super(name);
        }

        @Override
        public void run()
        {
            try
            {
                System.out.println(this.getName() + "准备去要锁了");
                if(lock.tryLock(2, TimeUnit.SECONDS))
                {
                    System.out.println(this.getName() + "得到了锁");
                    // 这里休眠时间比上面的超时时间长，其它线程就得不到锁，于是执行下面的分支
                    TimeUnit.SECONDS.sleep(3);
                }
                else
                {
                    System.out.println(this.getName() + "没有得到锁");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if(lock.isHeldByCurrentThread())
                {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        T t1 = new T("线程1");
        T t2 = new T("线程2");
        FutureTask futureTask;
        t1.start();
        t2.start();
        Thread.sleep(5*1000);
    }

}
