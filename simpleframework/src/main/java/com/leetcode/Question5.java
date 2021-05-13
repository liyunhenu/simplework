package com.leetcode;

public class Question5 {
    /**
     * 最长回文子串babad
     * 输出bad    aba也是一个有效答案
     */


    public static void main(String[] args) {
        String input = new String("babad");
        System.out.println(getMaxHuiWenChuan(input));
    }

    /**
     * 暴力求解
     * 遍历从i开始到j结束的长度比之前的发现的最长回文串常子串，判断是不是回文串
     *
     * @param input
     * @return
     */
    private static String getMaxHuiWenChuan(String input) {
        if (input.length() == 1) {
            return input;
        }
        char[] s = input.toCharArray();
        int n = s.length;
        int maxLength = 0;
        int left = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i; j < n; j++) {
                if ((j - i + 1) > maxLength && isHuiWennChuan(s, i, j)) {
                    maxLength = j - i + 1;
                    left = i;
                }
            }
        }
        return input.substring(left, left + maxLength);
    }

    /**
     * 动态规划，当前状态与之前状态有密切关系
     * P{i,j}=P{i+1,j-1}&&(S[i]==S[j])
     *
     * @param input
     * @return
     */
    private static String getMaxHuiWenChuan1(String input) {
        return null;
    }

    private static boolean isHuiWennChuan(char[] s, int i, int j) {
        if (i == j) {
            return true;
        }
        while (i < j) {
            if (s[i] == s[j]) {
                i = i + 1;
                j = j - 1;
            } else {
                return false;
            }
        }
        return true;
    }
}
