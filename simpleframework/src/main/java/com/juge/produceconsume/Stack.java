package com.juge.produceconsume;

import java.util.LinkedList;

public class Stack {

    LinkedList list = new LinkedList();


    public  synchronized void push(Object x) {
        System.out.println("get lock ");
        synchronized(list) {
            list.addLast( x );
            System.out.println("push end notify");
            list.notify();

        }
    }

    public synchronized Object pop()
            throws Exception {
        synchronized(list) {
            if( list.size() <= 0 ) {
                list.wait();
            }
            return list.removeLast();
        }
    }
}
