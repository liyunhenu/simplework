package com.leetcode.j0131partition;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {
    List<List<String>> res = null;

    public List<List<String>> partition(String s) {
        res = new ArrayList<>();

        Stack<String> huiwen = new Stack<>();

        dfs(s, 0, huiwen);
        return res;

    }

    private void dfs(String s, int index, Stack<String> huiwen) {
        if (index == s.length()) {
            if (huiwen.size() > 0) {
                res.add(stackToList(huiwen));
                return;
            }
        } else if (index >= 0 && index < s.length()) {
            int i = 1;
            while (index + i <= s.length()) {
                String next = s.substring(index, index + i);//往有拓展
                if (isHuiwen(next)) {
                    huiwen.push(next);
                    dfs(s, index + i, huiwen);
                    huiwen.pop();
                }
                i++;
            }
        }
    }

    private boolean isHuiwen(String next) {
        if (next == null || "".equals(next)) return false;
        if (next.length() == 1) return true;
        int middle = (next.length() - 1) / 2;
        int i = 0;
        while (i <= middle) {
            if (next.charAt(i) != next.charAt(next.length() - 1 - i)) return false;
            i++;
        }

        return true;
    }

    private List<String> stackToList(Stack<String> huiwen) {
        if (huiwen.isEmpty()) return null;
        List<String> list = new ArrayList<>();
        for (String tmp : huiwen) {
            list.add(tmp);
        }
        return list;
    }


    public static void main(String[] args) {

        String s = "efe";
        Solution solution = new Solution();
        //System.out.println(solution.isHuiwen("aaa"));

        List<List<String>> result = solution.partition(s);
        StringBuilder sb = new StringBuilder();
        if (result != null && result.size() > 0) {
            sb.append("[");
            for (List<String> tmp : result) {
                sb.append("[");
                for (String tmp1 : tmp) {
                    sb.append(tmp1).append(",");
                }
                sb.append("]");
                sb.append(",");
            }
            sb.append("]");
        }
        System.out.println(sb.toString());
    }
}
