package com.imooc.heap;

public class Test {

    public static void main(String[] args) {
        int[] input=new int[]{2,4,6,3,8,9,1,0};
        MyHeap myHeap=new MyHeap();
        for (int i = 0; i < input.length; i++) {
            System.out.print(input[i]+",");
        }
        System.out.println();
        input=myHeap.sort(input);
        for (int i = 0; i < input.length; i++) {
            System.out.print(input[i]+",");
        }
        System.out.println();
    }
}
