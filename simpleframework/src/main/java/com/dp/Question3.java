package com.dp;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a non-empty array containing only positive integers,
 * find if the array can be partitioned into two subsets
 * such that the sum of elements in both subsets is equal.
 * 数组分割问题
 * 1. Each of the array element will not exceed 100.
 * <p>
 * 2. The array size will not exceed 200
 * Input: [1, 5, 11, 5]
 * <p>
 * Output: true
 */

public class Question3 {

    public static void main(String[] args) {
        int[] input = {1, 5, 11, 5};
        System.out.println(equalSubSets(input));
    }


    public static Boolean equalSubSets(int[] nums) {
        int sum = 0;
        for (int num : nums
        ) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        boolean[][] dp = new boolean[nums.length + 1][target + 1];
        dp[0][0] = true;
        for (int i = 1; i <= nums.length; i++) {
            dp[i][0]=true;//初始化边界
            for (int j = 1; j <= target; j++) {
                dp[0][j]=false;//初始化边界
                dp[i][j] = dp[i - 1][j];
                if (j - nums[i - 1] >= 0) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        int position=0;
        for (int i = 1; i <=nums.length; i++) {
            if(dp[i][target]==true){
                position=i;
                break;
                //return true;
            }
        }
        List<Integer> pList=new LinkedList<>();
        int j1=target;
        for (int i = position; i >=1 ; i--) {
            if(dp[i][j1]!=dp[i-1][j1]){
                pList.add(nums[i]);
                j1=j1-nums[i-1];
            }
        }
        for (Integer p:pList
             ) {
            System.out.println(p+",");
        }

        if(position!=0){
            return true;
        }
        return false;
    }

}
