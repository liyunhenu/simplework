package com.imooc.singleton;

public class EnumSingleTon {

    public static EnumSingleTon getInstance(){
        return EnumHolder.HOLDER.instance;
    }

    private EnumSingleTon(){

    }

    enum EnumHolder{
        HOLDER;
        private EnumSingleTon instance;
        EnumHolder(){
          instance=new EnumSingleTon();
        }
    }
}
