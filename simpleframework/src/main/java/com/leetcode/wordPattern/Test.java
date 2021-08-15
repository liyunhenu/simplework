package com.leetcode.wordPattern;

public class Test {

    public static void main(String[] args) {
        String pattern = "abba";
        String s = "dog cat cat fish";
        Solution solution = new Solution();
        System.out.println(solution.wordPattern(pattern, s));
    }
}
