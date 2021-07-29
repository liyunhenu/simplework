package com.leetcode.findKthLargest;

import java.util.Random;

public class Solution {
    //nums中第k大的元素
    public int findKthLargest(int[] nums, int k) {
        return findKthLargest(nums, nums.length+1 - k, 0, nums.length - 1);
    }

    //寻找nums从left--right中第k个的元素
    public int findKthLargest(int[] nums, int k, int left, int right) {
        //第k大的元素     k右侧有k-1个元素   k坐标nums.length-1-(k-1)
        int p = partition(nums, left, right);
        if (p == (left + k - 1)) {
            return nums[p];
        } else if (p > (left + k - 1)) {//在p的左侧继续寻找
            return findKthLargest(nums, k, left, p - 1);
        } else {//在p的右侧继续寻找
            return findKthLargest(nums, k - (p - left + 1), p + 1, right);
        }
    }

    //
    public int partition(int[] nums, int left, int right) {
        if(left==right){
           return left;
        }
        Random random = new Random();
        int tmp = left + random.nextInt((right - left+1));
        swap(nums, tmp, right);
        int pivot = nums[right];//分割点
        int i = left;//[left,i)<pivot的元素
        int j = right - 1;//(j,right-1]>pivot的元素
        while (i <= j) {
            if (nums[i] < pivot) {
                i++;
            } else {
                swap(nums, i, j);
                j--;
            }
        }
        swap(nums, i, right);//将分割点放到分隔位置
        return i;
    }

    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


}
