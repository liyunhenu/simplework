package com.leetcode.ispalindrome;

class Solution1 {
    public boolean isPalindrome(int x) {

        if (x < 0) return false;

        int copy = x;
        int value = 0;
        while (x > 0) {
            value = value * 10 + x % 10;
            x = x / 10;
        }
        return value == copy;
    }

}
