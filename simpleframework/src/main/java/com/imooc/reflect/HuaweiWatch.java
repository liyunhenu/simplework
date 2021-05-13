package com.imooc.reflect;


public class HuaweiWatch {

    private Integer price;

    public String size;

    public HuaweiWatch(){

    }

    public HuaweiWatch(int price){
        this.price=price;
    }


    public String sayHello(){

        return "hello";

    }

    private String sayHello1(){
        return "private hello";
    }

    public String sayHello2(String word){
        return "hello!   "+word;
    }
}
