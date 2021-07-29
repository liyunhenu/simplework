package com.leetcode.merge;

public class Test {
    public static void main(String[] args) {
      int[] nums1=new int[]{1};
      int[] nums2=new int[]{};
      showArr(nums1);
      Solution solution=new Solution();
      solution.merge(nums1,1,nums2,0);
      showArr(nums1);

    }

    public static void showArr(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+",");
        }
        System.out.println();
    }
}
