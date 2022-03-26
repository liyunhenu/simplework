package com.juge.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.time.Instant;

/**
 * Guava 实现限流
 */
public class RateLimiterExample {
    public static void main(String[] args) {
        // 每秒产生 10 个令牌（每 100 ms 产生一个）
        RateLimiter rt = RateLimiter.create(10);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                // 获取 1 个令牌
                rt.acquire();
                System.out.println("正常执行方法，ts:" + Instant.now());
            }).start();
        }
    }
}

