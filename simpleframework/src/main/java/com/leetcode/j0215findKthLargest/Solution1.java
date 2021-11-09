package com.leetcode.j0215findKthLargest;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution1 {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> priorityQueue=new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            if(priorityQueue.size()<k){
                priorityQueue.offer(nums[i]);
            }else if(priorityQueue.size()==k&&nums[i]>priorityQueue.peek()){
                priorityQueue.poll();
                priorityQueue.offer(nums[i]);
            }
        }
        return priorityQueue.poll();
    }


    public static void main(String[] args) {
        int[] nums=new int[]{3,2,3,1,2,4,5,5,6};
        int k=4;
        Solution1 solution=new Solution1();
        System.out.println(solution.findKthLargest(nums,k));
    }
}
