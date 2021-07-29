package com.leetcode.findKthLargest;

public class Solution1 {
    public int findKthLargest(int[] nums, int k) {
        if (nums.length == 1) {
            return nums[0];
        }
        buildMaxHeap(nums);
        for (int i = 0; i < k-1; i++) {
            swap(nums, 0, nums.length - 1 - i);
            shiftDown(nums, nums.length - 2 - i);
        }
        return nums[0];
    }

    private void buildMaxHeap(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            shiftUp(nums, i);
        }
    }

    //上移操作，建最大堆使用

    /**
     * 0
     * 1   2
     * 3 4  5 6
     *
     * @param nums
     * @param i
     */
    public void shiftUp(int[] nums, int i) {
        if (i < 1) {
            return;
        }
        while (i>0){
            if (nums[(i - 1) / 2] < nums[i]) {
                swap(nums, (i - 1) / 2, i);
            }
            i=(i-1)/2;
        }

    }

    //下移操作，删除堆顶元素后，重构最大堆
    public void shiftDown(int[] nums, int end) {
        for (int i = 0; i * 2 + 1 <= end; i++) {
            if (nums[i] < nums[2 * i + 1]) {
                swap(nums, i, 2 * i + 1);
            }
            if (2 * i + 2 <= end) {
                if (nums[i] < nums[i * 2 + 2]) {
                    swap(nums, i, 2 * i + 2);
                }
            }
        }
    }

    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void showArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }

}
