package com.example.demo1.util;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * HttpClientConnectionMonitorThread class
 * <p>使用管理器，管理HTTP连接池，无效链接定期清理功能</p>
 *
 * @author wangqiang
 * @date 2018/12/13
 */
public class HttpClientConnectionMonitorThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientConnectionMonitorThread.class);
    private final PoolingHttpClientConnectionManager connectionManager;
    private volatile boolean shutdown;

    public
    HttpClientConnectionMonitorThread(PoolingHttpClientConnectionManager connectionManager) {
        super();
        this.setName("http-connection-monitor");
        this.setDaemon(true);
        this.connectionManager = connectionManager;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(2000); //等待2秒
                    //关闭过期的连接
                    connectionManager.closeExpiredConnections();
                    //选择关闭空闲30秒的连接
                    connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
                    logger.info("过期连接已经被清理了");
                    //wait(1000);//等1秒
                    PoolStats poolStats=connectionManager.getTotalStats();
                    if(poolStats!=null){
                        logger.info("最大连接数"+poolStats.getMax());
                        logger.info("持久连接数"+poolStats.getLeased());
                        logger.info("阻塞连接数"+poolStats.getPending());
                        logger.info("空闲连接数"+poolStats.getAvailable());
                    }
                }
            }
        } catch (InterruptedException e) {
            logger.error("HttpClientConnectionMonitorThread error", e);
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
