package com.leetcode.merge;

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if(n==0||nums2.length==0){return;}
        int[] tmp = new int[m + n];
        int[] tmp1 = new int[m + 1];
        System.arraycopy(nums2, 0, tmp1, 0, nums2.length);
        nums1[m] = Integer.MAX_VALUE;
        tmp1[n] = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        int k = 0;
        while (k < (m + n)) {
            if (nums1[i] < tmp1[j]) {
                tmp[k] = nums1[i];
                i++;
            } else {
                tmp[k] = tmp1[j];
                j++;
            }
            k++;
        }
        for (int p = 0; p < tmp.length; p++) {
            nums1[p] = tmp[p];
        }
    }
}
