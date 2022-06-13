package com.leetcode.j0017letterCombinations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    public static final Map<Character, String> element = new HashMap() {
    };

    static {
        element.put('0', " ");
        element.put('1', "");
        element.put('2', "abc");
        element.put('3', "def");
        element.put('4', "ghi");
        element.put('5', "jkl");
        element.put('6', "mno");
        element.put('7', "pqrs");
        element.put('8', "tuv");
        element.put('9', "wxyz");
    }


    public List<String> letterCombinations(String digits) {
        List<String> result = letterCombinations(digits, 0);
        return result;
    }

    private List<String> letterCombinations(String digits, int i) {

        List<String> result = new ArrayList<>();
        if (i >= digits.length()) {
            result.add("");
            return result;
        }
        String letter = element.get(digits.charAt(i));//当前号码对应的字母可能性

        List<String> tmp = letterCombinations(digits, i + 1);

        if (letter.length() > 0) {
            for (int j = 0; j < letter.length(); j++) {
                for (int k = 0; k < tmp.size(); k++) {
                    result.add(letter.substring(j, j + 1) + tmp.get(k));
                }

            }
        } else {
            result = tmp;
        }


        return result;
    }

    public static void main(String[] args) {
        String input = "23";
        Solution solution = new Solution();
        List<String> result = solution.letterCombinations(input);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }


}
