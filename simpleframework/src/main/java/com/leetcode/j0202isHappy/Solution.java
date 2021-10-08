package com.leetcode.j0202isHappy;

import java.util.HashSet;
import java.util.Set;

public class Solution {


    private Set<Integer> sumSet = new HashSet<>();

    public boolean isHappy(int n) {

        int sum = 0;
        while (n > 0) {
            int tmp = n % 10;
            sum += tmp * tmp;
            n = n / 10;
        }
        if (sumSet.contains(sum)) {
            return false;
        }
        if (sum > 1) {
            sumSet.add(sum);
            return isHappy(sum);
        } else {
            return true;
        }
    }
}
