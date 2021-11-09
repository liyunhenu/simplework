package com.leetcode.j0215findKthLargest;

import java.util.Arrays;

class Solution {
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length-k];
    }


    public static void main(String[] args) {
        int[] nums=new int[]{3,2,1,5,6,4};
        int k=4;
        Solution solution=new Solution();
        System.out.println(solution.findKthLargest(nums,k));
    }
}
