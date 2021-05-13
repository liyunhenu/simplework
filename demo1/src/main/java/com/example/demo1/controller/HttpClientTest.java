package com.example.demo1.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo1.util.HttpClientUtils;
import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {


    private static String requestUrl = "http://localhost:8081/ppp/hello";
    private static String requestUrl1 = "http://localhost:8080/ppp/hello1";


    public static void main(String[] args) throws InterruptedException {
        System.out.println("开始测试");
        while(true){
            for (int i = 0; i < 30; i++) {
                new Thread(() -> {
                    long start=System.currentTimeMillis();
                    Map<String, Object> response = doPost(3);
                    long stop=System.currentTimeMillis();
                    long costTime=stop-start;
                    System.out.println("第一轮返回结果" + response+"耗时："+costTime+"毫秒");
                }).start();
            }
            Thread.sleep(1000);
        }

        /*System.out.println("结束测试");
        Thread.sleep(10000);
        System.out.println("等待结束");*/
        /*while (true) {

        }*/




        /*System.out.println("HHHHHHHHHHHHHHHFFFFFFFFFFFBBBBBBB,我是分隔符");
        for (int i = 0; i <10 ; i++) {
            new Thread(() -> {
                Map<String,Object> response=doPost(1);
                System.out.println("第二轮返回结果"+response);
            }).start();
        }
        Thread.sleep(3000);
        //System.out.println("开始清理过期连接了");
        for (int i = 0; i <10 ; i++) {
            new Thread(() -> {
                Map<String,Object> response=doPost(1);
                System.out.println("第三轮返回结果"+response);
            }).start();
        }*/
        //Thread.sleep(6000);

    }

    public static Map<String, Object> doPost(Integer timeWait) {
        Map<String, Object> result = new HashMap<>();
        JSONObject requestParams = new JSONObject();
        requestParams.put("time", timeWait);
        JSONObject response = HttpClientUtils.doPost(requestUrl, requestParams);
        if (response == null) {
            result.put("code", "FFFFFF");
            result.put("MSG", "error");
        } else {
            result.put("code", "AAAAAA");
            result.put("MSG", response.toJSONString());
        }
        return result;

    }

    public static Map<String, Object> doPost1() {
        JSONObject requestParams = new JSONObject();
        JSONObject response = HttpClientUtils.doPost(requestUrl1, requestParams);

        Map<String, Object> result = new HashMap<>();
        result.put("code", "AAAAAA");
        result.put("MSG", response.toJSONString());
        return result;

    }
}
