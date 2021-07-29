package com.leetcode.minPairSum;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Solution {
    public int minPairSum(int[] nums) {
        Arrays.sort(nums);//最小
        int maxSum=Integer.MIN_VALUE;
        for (int i = 0; i < nums.length/2; i++) {
            maxSum=Math.max(maxSum,nums[i]+nums[nums.length-1-i]);
        }
        if(maxSum==Integer.MIN_VALUE) return 0;
        return maxSum;
    }
}
