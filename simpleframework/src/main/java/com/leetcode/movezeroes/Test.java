package com.leetcode.movezeroes;

public class Test {

    public static void main(String[] args) {
        int[] input=new int[]{2,1};
        showArr(input);
        Solution solution=new Solution();
        solution.moveZeroes(input);
        showArr(input);

    }

    public static void showArr(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+",");
        }
        System.out.println();
    }
}
