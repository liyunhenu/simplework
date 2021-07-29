package com.leetcode.findKthLargest;

public class Test {

    public static void main(String[] args) {
        int[] input=new int[]{3,2,1,5,6,4};
        //showArr(input);
        /*Solution solution=new Solution();
        System.out.println(solution.findKthLargest(input,1));*/
        Solution1 solution=new Solution1();
        System.out.println(solution.findKthLargest(input,2));
    }

    public static void showArr(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+",");
        }
        System.out.println();
    }
}
