package com.leetcode.intersection;

public class Test {



    public static void main(String[] args) {
        int[] nums1 = new int[]{1,2,2,1};
        int[] nums2 = new int[]{2,2};
        Solution solution=new Solution();

        int[] result=solution.intersection(nums1,nums2);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]+",");
        }
    }
}
