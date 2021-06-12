package com.imooc.singleton;

public class StarvingSingleTon {
    private static final StarvingSingleTon instance=new StarvingSingleTon();

    private StarvingSingleTon() {
    }

    public static StarvingSingleTon getInstance(){
        return instance;
    }
}
