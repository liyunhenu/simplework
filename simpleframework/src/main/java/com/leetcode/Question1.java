package com.leetcode;

import java.util.HashMap;

/**
 * 两数相加
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Question1 {
    public static void main(String[] args) {
        int[] nums = {2, 5, 7, 11, 15};
        int target = 9;
        int[] result = twoSum1(nums, target);
        for (int a : result
        ) {
            System.out.println(a);
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        if (nums.length <= 1) {
            return result;
        }
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    public static int[] twoSum1(int[] nums, int target) {
        int[] result = new int[2];
        HashMap<Integer, Integer> hashMap = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(nums[i])) {
                result[0] = hashMap.get(nums[i]);
                result[1] = i;
            }
            hashMap.put(target - nums[i], i);
        }
        return result;
    }
}
