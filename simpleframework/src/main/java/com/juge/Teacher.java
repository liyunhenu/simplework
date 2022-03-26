package com.juge;


public class Teacher extends Person {
    public int b;

    public static void main(String arg[]) {
        Person p = new Person();
        Teacher t = new Teacher();
        p=t;
        t= (Teacher) p;
        int i; // point x
        switch ("1"){
            case "1":
                System.out.println("支持");
                break;
            case "2":
                System.out.println("不支持");
                break;
            default:
                System.out.println("啥也不干");
        }

    }
}