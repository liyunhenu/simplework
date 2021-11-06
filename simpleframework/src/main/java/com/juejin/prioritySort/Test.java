package com.juejin.prioritySort;

import com.juejin.prioritySort.model.CouponType;
import com.juejin.prioritySort.model.OrderModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

    /*给大家出个简单的排序算法题，看看怎么排序最优：
    已知：
            1）有四种类型的优惠券，例如，A，B，C，D， 优惠券都有额度，过期时间。
            2）每种类型优惠券数量不限制（0，1，N都可能，不考虑极值）
    求解：
    第一种排序方式：
            1）按照额度倒序
2）额度相等的时候，优惠券A拍前面
3）如果不是优惠券A，则按过期时间升序
4）时间一样，则按照 优惠券B，C，D顺序排序

    第二种排序方式：
            1）按照额度倒序
2）额度相等的时候，优惠券A、B顺序排序
3）如果不是优惠券A和B，则按过期时间升序
4）时间一样，则按照 C，D顺序排序*/

    public static void main(String[] args) throws ParseException {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        OrderModel orderModel1=new OrderModel(5.5,dateFormat.parse("2021-11-07 18:01:01"), CouponType.ONE);
        OrderModel orderModel2=new OrderModel(4.5,dateFormat.parse("2021-11-07 18:01:01"), CouponType.TWO);
        OrderModel orderModel3=new OrderModel(4.5,dateFormat.parse("2021-11-07 18:01:02"), CouponType.THREE);
        OrderModel orderModel4=new OrderModel(4.5,dateFormat.parse("2021-11-07 18:01:01"), CouponType.ONE);
        OrderModel orderModel5=new OrderModel(4.5,dateFormat.parse("2021-11-07 18:01:01"), CouponType.THREE);
        OrderModel orderModel6=new OrderModel(4.5,dateFormat.parse("2021-11-07 18:01:01"), CouponType.FOUR);
        List<OrderModel> list=new ArrayList<>();
        list.add(orderModel1);
        list.add(orderModel2);
        list.add(orderModel3);
        list.add(orderModel4);
        list.add(orderModel5);
        list.add(orderModel6);
        Collections.sort(list);
        //金额降序排列  第一优先级升序排列  时间升列 第二优先级降序
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
