package com.cmbcc;

import java.text.SimpleDateFormat;
import java.util.*;

public class Question4 {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        Set<String> yewuSet = new HashSet<>();//不包含重复数据的业务流水数据
        Set<String> paySet = new HashSet<>();//不包含重复数据的支付流水数据
        Set<String> yewuSet1 = new HashSet<>();//不包含业务流水字段的业务流水数据
        Set<String> paySet1 = new HashSet<>();//不包含业务流水的支付流水数据
        Map<String, Integer> repetNum = new HashMap<>();//重复
        Map<String, Integer> repetNum1 = new HashMap<>();//重复
        while (cin.hasNext()) {
            String tmp = cin.nextLine();
            String[] tmpArray = tmp.trim().split(",");
            if (tmp.equals("-1")) {
                break;
            }
            if (tmpArray.length == 5) {
                String tmpString = tmpArray[0];
                if (repetNum1.containsKey(tmpString)) {
                    int num = repetNum.get("tmp[0]");
                    num++;
                    repetNum.put(tmp, num);
                    if (num == 2) {//第二次出现就删除
                        yewuSet.remove(tmp);
                        yewuSet1.remove(tmpArray[0]);//业务流水
                    }
                } else {
                    repetNum.put(tmp, 1);
                    yewuSet.add(tmp);
                    yewuSet1.add(tmpArray[0]);//业务流水

                }
            }
        }


        while (cin.hasNext()) {
            String tmp = cin.nextLine();
            String[] tmpArray = tmp.trim().split(",");
            if (tmp.equals("-1")) {
                break;
            }
            if (tmpArray.length == 5) {
                String tmpString1 = tmpArray[0];
                if (repetNum1.containsKey(tmpString1)) {
                    int num = repetNum.get("tmp[0]");
                    num++;
                    repetNum.put(tmp, num);
                    if (num == 2) {
                        paySet.remove(tmp);//重复出现
                        paySet1.remove(tmpArray[1]);
                    }
                } else {
                    repetNum.put(tmp, 1);
                    paySet.add(tmp);
                    paySet1.add(tmpArray[1]);

                }
            }
        }
        //支付数据中重复出现的数据
        for (Map.Entry<String, Integer> entry : repetNum.entrySet()) {
            String key = entry.getKey();
            Integer num = entry.getValue();
            if (num > 1) {
                //业务流水数据出现重复
            }
        }
        //支付数据中重复出现的数据
        for (Map.Entry<String, Integer> entry : repetNum1.entrySet()) {
            String key = entry.getKey();
            Integer num = entry.getValue();
            if (num > 1) {
                //支付流水出现重复度额次数
            }
        }

        Set<String> resultList = new HashSet<>();
        /*Object yewuArray=yewuSet.toArray();
        Object payArray=paySet.toArray();*/
        for (String yewu : yewuSet) {
            String[] yewuArray = yewu.trim().split(",");
            //String yewuliushui = yewuArray[0];//业务流水
            Boolean flag = false;
            for (String pay : paySet) {
                String[] payArray = pay.trim().split(",");
                if (yewuArray[0].equals(payArray[1])) {
                    if (yewuArray[2].equals(payArray[2])) {
                        //交易金额相同
                        if (yewuArray[3].equals(payArray[3])) {
                            //交易时间相同
                        } else {
                            //交易时间不同
                            resultList.add("");//D(交易金额数据差异),E(交易时间数据差异),DE(交易金额和交易时间数据差异)
                        }
                    } else {
                        //交易金额不同
                        if (yewuArray[3].equals(payArray[3])) {
                            //交易时间相同
                            resultList.add("");//D(交易金额数据差异),E(交易时间数据差异),DE(交易金额和交易时间数据差异)
                        } else {
                            //交易时间不同
                            resultList.add("");//D(交易金额数据差异),E(交易时间数据差异),DE(交易金额和交易时间数据差异)
                        }
                    }
                }
            }
            if (!flag) {
                //业务数据在支付数据中不存在
                resultList.add("");//D(交易金额数据差异),E(交易时间数据差异),DE(交易金额和交易时间数据差异)
            }


        }

    }

    public static class ErrorData implements Comparable {
        String yewuSerrial;
        String yewuMoney;
        String paySerial;
        String payMoney;
        String yewuTime;
        String payTime;

        public ErrorData(String yewuSerrial, String yewuMoney, String paySerial, String payMoney, String yewuTime, String payTime) {
            this.yewuSerrial = yewuSerrial;
            this.yewuMoney = yewuMoney;
            this.paySerial = paySerial;
            this.payMoney = payMoney;
            this.yewuTime = yewuTime;
            this.payTime = payTime;
        }

        @Override
        public String toString() {
            return yewuSerrial + "," + yewuMoney + "," + paySerial + "," + payMoney + "," + yewuTime + "," + payTime;
        }

        @Override
        public int compareTo(Object o) {
            int i = 0;
            if (o instanceof ErrorData) {
                ErrorData errorData = (ErrorData) o;
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss:");
                /*if(this.yewuTime>errorData.yewuTime){

                }*/
            }
            return i;
        }
    }
}
