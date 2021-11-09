package com.leetcode.smallestK;

import java.util.Comparator;
import java.util.PriorityQueue;

class Solution {
    public int[] smallestK(int[] arr, int k) {
        if(k==0) return new int[0];
        int[] result = new int[k];

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0; i < arr.length; i++) {
            if (priorityQueue.size() < k) {
                priorityQueue.offer(arr[i]);
            } else if (priorityQueue.size() == k) {
                if(arr[i] < priorityQueue.peek()){
                    priorityQueue.poll();
                    priorityQueue.offer(arr[i]);
                }
            }
        }
        for (int i = 0; i < k; i++) {
            result[i] = priorityQueue.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3};
        int k = 0;
        Solution solution = new Solution();
        int[] result = solution.smallestK(arr, k);
        System.out.print("[");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + ",");
        }
        System.out.print("]");
    }
}
