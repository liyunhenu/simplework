package com.leetcode.movezeroes;

public class Solution {
    public void moveZeroes(int[] nums) {
        int k=0;//[0,k)是非零元素
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=0){
                if(i!=k){
                    swap(nums,i,k);
                }
                k++;

            }
        }
    }

    public void swap(int[] arr,int i,int j){
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }
}
