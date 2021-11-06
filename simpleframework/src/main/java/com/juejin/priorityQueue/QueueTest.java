package com.juejin.priorityQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class QueueTest {


    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue=new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer x, Integer y) {
                return (y - x);
            }
        });
        priorityQueue.offer(1);
        priorityQueue.offer(2);
        priorityQueue.offer(3);
        priorityQueue.offer(4);
        priorityQueue.offer(5);
        for (int i = 0; i < 5; i++) {
            System.out.println(priorityQueue.poll());
        }

    }
}
