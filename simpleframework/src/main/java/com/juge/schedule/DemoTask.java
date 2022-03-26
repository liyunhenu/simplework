package com.juge.schedule;

import com.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DemoTask implements Runnable {

    private Logger logger= LoggerFactory.getLogger(DemoTask.class);

    private int taskNum;

    public DemoTask(int taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void run() {

        logger.info(StringUtils.center("正在执行" + taskNum, 20, "="));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(StringUtils.center("执行完毕" + taskNum, 20, "="));
    }
}
