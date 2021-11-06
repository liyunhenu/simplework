package com.juejin.prioritySort.model;

public enum CouponType {
    ONE("第一种优惠券",1,0),
    TWO("第二种优惠券",2,1),
    THREE("第三种优惠券",2,2),
    FOUR("第四种优惠券",2,3);

    String desc;
    int firstPriority;
    int secondPriority;

    CouponType(String desc, int i, int i1) {
        this.desc=desc;
        this.firstPriority=i;
        this.secondPriority=i1;
    }
}
