package com.leetcode.stringtwointeger;

class Solution {
    public static int myAtoi(String s) {
        String s1 = s.trim().replace(" ", "");
        if (s1.equals("")) {
            return 0;
        }
        int signal = 0;
        String first = s1.substring(0, 1);
        if ("-".equals(first)) {
            signal = -1;
            s1 = s1.substring(1);
        } else {
            signal = 1;
        }
        int begin = 0;
        int end = 0;
        Boolean isfirst = false;
        for (int i = 0; i < s1.length(); i++) {
            char tmp = s1.charAt(i);
            if (tmp >= '0' && tmp <= '9') {
                if (!isfirst) {
                    begin = i;
                    isfirst = true;
                }
            } else {
                if (isfirst) {
                    end = i;
                    break;
                }
            }
        }
        if (isfirst && end == 0) {
            end = s1.length();
        }
        //System.out.println(s1.substring(begin,end));
        if (begin == end) {
            return 0;
        } else {
            long b = Long.parseLong(s1.substring(begin, end));
            if (signal == 1) {
                return (int) Math.min(Integer.MAX_VALUE, b);
            } else {
                return (int) (signal * Math.min(-(long)Integer.MIN_VALUE, b));
            }
        }


    }

    public static void main(String[] args) {
        String input = new String("  -42");
        System.out.println(myAtoi(input));
    }
}
