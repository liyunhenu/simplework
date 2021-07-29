package com.leetcode.removeelment;

import java.util.Collection;
import java.util.Collections;

public class Test {

    public static void main(String[] args) {
        int[] input = new int[]{-1,-1,1,1,1,1,2,3,3};
        Solution solution = new Solution();
        int result = solution.removeDuplicates(input);
        for (int i = 0; i < result; i++) {
            System.out.print(input[i] + ",");
        }
    }
}
