package com.leetcode.validutf8;

public class Test {

    public static void main(String[] args) {
        //int[] data = {235, 140, 4};
        int[] data={197, 130, 1};
        Solution solution=new Solution();
        System.out.println(solution.validUtf8(data));
    }
}
