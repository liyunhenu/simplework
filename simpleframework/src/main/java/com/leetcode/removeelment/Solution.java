package com.leetcode.removeelment;

public class Solution{
    public int removeDuplicates(int[] nums) {

        int k=1;  //k表示去除重复元素的有序数组  [0,k)开区间
        int last=Integer.MAX_VALUE;
        int lastlast=Integer.MAX_VALUE;
        if(nums.length<=1){
            return nums.length;
        }
        for(int i=1;i<nums.length;i++){
            lastlast=last;
            last=nums[i-1];
            if(nums[i]!=last){
                if(i!=k){
                    nums[k]=nums[i];
                }
                k++;
            }else{
                if(nums[i]!=lastlast){
                    if(i!=k){
                        nums[k]=nums[i];
                    }
                    k++;
                }
            }

        }
        return k;
    }
}
