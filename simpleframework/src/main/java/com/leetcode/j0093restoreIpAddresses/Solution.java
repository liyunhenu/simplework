package com.leetcode.j0093restoreIpAddresses;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {
    private List<String> res = new ArrayList<>();//每个数组中放一分割完成的ip的四段数字例如[111,225,113,121]

    public List<String> restoreIpAddresses(String s) {

        Stack<Integer> ip = new Stack<>();

        dfs(s, 0, ip);

        return res;

    }

    private void dfs(String s, int index, Stack<Integer> ip) {
        if (index == s.length()) {
            if (ip.size() == 4) {
                res.add(convertToString(ip));
                return;
            }
        } else if (index == 0) {
            ip.push(s.charAt(0) - '0');
            dfs(s, 1, ip);
        } else if (index < s.length() && index > 0) {
            //一直沿着分支往下找
            if (ip.size() < 4) {
                //System.out.println(ip.size());
                ip.push(s.charAt(index) - '0');
                dfs(s, index + 1, ip);
                ip.pop();
            }
            //左右遍历
            int next = ip.peek() * 10 + s.charAt(index) - '0';
            if (next <= 255 && ip.peek() != 0) {
                ip.pop();
                ip.push(next);
                dfs(s, index + 1, ip);
                ip.pop();
                ip.push(next / 10);
            }
        }
    }

    private String convertToString(Stack<Integer> ip) {
        String result = "";
        if (ip == null && ip.size() == 0) return result;
        for (Integer i : ip) {
            result += i + ".";
        }
        return result.substring(0, result.length() - 1);
    }


    public static void main(String[] args) {
        String s = "101023";
        Solution solution = new Solution();
        System.out.println(solution.restoreIpAddresses(s));
    }


}
