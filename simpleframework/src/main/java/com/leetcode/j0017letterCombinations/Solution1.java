package com.leetcode.j0017letterCombinations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution1 {
    List<String> res = new ArrayList<>();
    static Map<Integer, String> directory = new HashMap<>();

    static {
        directory.put(0, " ");
        directory.put(1, "");
        directory.put(2, "abc");
        directory.put(3, "def");
        directory.put(4, "ghi");
        directory.put(5, "jkl");
        directory.put(6, "mno");
        directory.put(7, "pqrs");
        directory.put(8, "tuv");
        directory.put(9, "wxyz");
    }

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) return res;
        DFS(digits, 0, "");
        return res;

    }

    //字符串，当前分支层级别
    private void DFS(String s, int index, String now) {
        if (index == s.length()) {
            res.add(now);
            return;
        } else if (index >=0 && index < s.length()) {
            String candidate = directory.get(s.charAt(index) - '0');
            if (candidate != null) {
                for (int i = 0; i < candidate.length(); i++) {
                    DFS(s, index + 1, now + candidate.charAt(i));
                }
            }
        }
    }

    public static void main(String[] args) {
        String input = "23";
        Solution1 solution = new Solution1();
        List<String> result = solution.letterCombinations(input);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }
}
