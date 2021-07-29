package com.imooc.heap;

public class MyHeap {
    int[] buildMaxHeap(int[] input) {
        int length = input.length;
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = input[i];
            shiftup(result, i);
        }
        return result;
    }

    void shiftup(int[] heap, int i) {
        for (int j = i; j >= 0; ) {
            if (heap[(j - 1) / 2] < heap[j]) {
                swap(heap, (j - 1) / 2, j);
                j = (j - 1) / 2;
            }else {
                break;
            }
        }
    }

    public void swap(int[] s, int i, int j) {
        int tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }

    //j是结束位置i，在[0-j)之间进行上移动操作
    void shiftDown(int[] s,int j){
        for(int i=0;i<=(j-1)/2;i++){
            if(i*2+1<j){
                if(s[i]<s[i*2+1]){
                    swap(s,i,2*i+1);
                }
            }
            if(i*2+2<j){
                if(s[i]<s[i*2+2]){
                    swap(s,i,i*2+2);
                }
            }


        }
    }

    public int[] sort(int[] s){
        if(s.length<=1) return s;
        s=buildMaxHeap(s);
        for (int i = 0; i < s.length; i++) {
            System.out.print(s[i]+",");
        }
        System.out.println();
        for (int i=s.length-1;i>0;i--){
            swap(s,0,i);
            shiftDown(s,i);
        }

        return s;
    }


}
