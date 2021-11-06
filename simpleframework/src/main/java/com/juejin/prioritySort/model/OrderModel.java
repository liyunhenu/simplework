package com.juejin.prioritySort.model;

import java.util.Date;

public class OrderModel implements Comparable<OrderModel> {

    double money;//额度
    Date expireDate;//过期时间
    CouponType couponType;//优惠券种类

    public OrderModel() {
    }

    public OrderModel(double money, Date expireDate, CouponType couponType) {
        this.money = money;
        this.expireDate = expireDate;
        this.couponType = couponType;
    }

    @Override
    public int compareTo(OrderModel o) {
        double diff=o.money-this.money;//额度降序排列
        if(diff!=0D){
            return diff>0?1:-1;
        }
        int diffFirstPriority=this.couponType.firstPriority-o.couponType.firstPriority;//第一优先级升序排列
        if(diffFirstPriority!=0){
            return diffFirstPriority;
        }
        long diffTime=this.expireDate.getTime()-o.expireDate.getTime();//过期时间升序
        if(diffTime!=0L){
            return diffTime>0L?1:-1;
        }
        int diffSecondPriority=this.couponType.secondPriority-o.couponType.secondPriority;//第二优先级升序排列
        return diffSecondPriority;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "money=" + money +
                ", expireDate=" + expireDate +
                ", couponType=" + couponType +
                '}';
    }
}
