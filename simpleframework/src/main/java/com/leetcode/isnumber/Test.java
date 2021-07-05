package com.leetcode.isnumber;

public class Test {


    public static void main(String[] args) {

        String[] inputStrings=new String[]{"2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"};
        //String[] inputStrings=new String[]{"abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"};
        for (int i = 0; i < inputStrings.length; i++) {
            Solution solution=new Solution();
            System.out.println(inputStrings[i]+"   isnumber: "+solution.isNumber(inputStrings[i]));
        }
        Solution solution=new Solution();
        System.out.println(solution.isNumber("4e"));

    }
}
