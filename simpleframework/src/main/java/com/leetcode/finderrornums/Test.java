package com.leetcode.finderrornums;

public class Test {

    public static void main(String[] args) {
        int[] input=new int[]{1,2,3,4,4};
        Solution solution=new Solution();
        int[] result=solution.findErrorNums(input);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]+",");
        }
    }
}
