package com.imooc.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {


    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        Class myclass  = Class.forName("com.imooc.reflect.HuaweiWatch");
        Object object  =  myclass.getDeclaredConstructor().newInstance();
        Method   method= myclass.getDeclaredMethod("sayHello");
        System.out.println(method.invoke(object));

        Method method1=myclass.getDeclaredMethod("sayHello2", String.class);
        System.out.println(method1.invoke(object,"liyun"));

        Method method2=myclass.getDeclaredMethod("sayHello1");
        Field field=myclass.getField("price");
        field.get(object);
        method2.setAccessible(true);
        System.out.println(method2.invoke(object));
        System.out.println(System.getProperty("java.ext.dirs"));


    }
}
