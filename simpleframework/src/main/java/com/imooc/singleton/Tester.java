package com.imooc.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Tester {


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        StarvingSingleTon starvingSingleTon=StarvingSingleTon.getInstance();
        System.out.println(starvingSingleTon);
        Class class1=StarvingSingleTon.class;
        Constructor constructor=class1.getDeclaredConstructor();
        constructor.setAccessible(true);
        System.out.println(constructor.newInstance());

        LazySingleTon lazySingleTon=LazySingleTon.getInstance();
        System.out.println(lazySingleTon);
        Class class2=LazySingleTon.class;
        Constructor constructor1=class2.getDeclaredConstructor();
        constructor1.setAccessible(true);
        System.out.println(constructor1.newInstance());

        EnumSingleTon enumSingleTon=EnumSingleTon.getInstance();
        System.out.println(enumSingleTon);
        Class class3=EnumSingleTon.class;
        Constructor constructor2=class3.getDeclaredConstructor();
        constructor2.setAccessible(true);
        EnumSingleTon enumSingleTon1=(EnumSingleTon) constructor2.newInstance();
        System.out.println(enumSingleTon1);
    }
}
