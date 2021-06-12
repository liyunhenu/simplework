package com.imooc.timer;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import sun.jvm.hotspot.utilities.WorkerThread;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class TimerTest {

    public static void main(String[] args) throws InterruptedException {

        time();
        // time1();
        time5();
    }


    public static void time() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("do some thing!!!");
            }


        }, 1000);
    }


    public static void time1() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("do something");
            }

        }, 1000, 2000);//延迟1000ms后，每2000ms延迟执行一次
    }


    public static void time2() {
        Timer timer = new Timer();
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.HOUR_OF_DAY, 12);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        Date mydate = calender.getTime();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("do something");
            }
        }, mydate, 1000 * 60 * 60 * 24);//固定延迟后，指定频率执行，开始时间频率，

    }


    public static void time5() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 1000, TimeUnit.NANOSECONDS, new ArrayBlockingQueue<>(200), new CustomizableThreadFactory("myScheduleTask"), new ThreadPoolExecutor.DiscardOldestPolicy());

        ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(10, new CustomizableThreadFactory("myscheduleThread"), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 5; i++) {
            schedule.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("do something");
                }
            }, 1000, TimeUnit.MILLISECONDS);//单次执行
        }
        Thread.sleep(30000);
        System.out.println("now time" + new Date());
        schedule.shutdown();
        while (!schedule.isShutdown()) {
            Thread.sleep(1000);
            System.out.println("wait for schedulePool shutdown");
        }
        System.out.println("end time " + new Date());
        schedule=new ScheduledThreadPoolExecutor(10,new CustomizableThreadFactory("111"),new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 5; i++) {
            schedule.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println("定时执行任务");
                }
            }, 1000, 2000, TimeUnit.MILLISECONDS);
        }
        //WorkerThread workerThread=new WorkerThread("do");


    }
}
