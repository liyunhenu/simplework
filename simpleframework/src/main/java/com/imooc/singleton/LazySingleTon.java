package com.imooc.singleton;

public class LazySingleTon {
    private volatile static LazySingleTon instance;
    private LazySingleTon(){}
    public static LazySingleTon getInstance(){
        if(instance==null){
            synchronized(LazySingleTon.class){
                if(instance==null){
                    instance=new LazySingleTon();
                    return instance;
                }
            }
        }
        return instance;
    }

}
