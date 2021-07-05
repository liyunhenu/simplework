package com.leetcode.finderrornums;

class Solution {
    public int[] findErrorNums(int[] nums) {
        int length=nums.length;
        int[] p=new int[length+1];
        int[] result=new int[2];
        for(int i=0;i<length;i++){
            p[nums[i]]++;
        }
        for(int i=1;i<p.length;i++){
            if(p[i]==2) result[0]=i;
            if(p[i]==0) result[1]=i;
        }
        return result;
    }
}
