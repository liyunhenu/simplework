package com.leetcode.ispalindrome;

class Solution {
    public boolean isPalindrome(int x) {

        if (x < 0) return false;


            String input = String.valueOf(x);

        int length = input.length();

        for (int i = 0; i < length / 2; i++) {
            if (input.charAt(i) == input.charAt(length - 1 - i)) {

            } else {
                return false;
            }

        }
        return true;
    }
}
