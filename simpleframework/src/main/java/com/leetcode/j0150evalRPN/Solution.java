package com.leetcode.j0150evalRPN;

import java.util.Stack;

public class Solution {

    public int evalRPN(String[] tokens) {

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            if (!tokens[i].equals("+") && !tokens[i].equals("-") && !tokens[i].equals("*") && !tokens[i].equals("/")) {
                stack.push(Integer.valueOf(tokens[i]));
            } else {
                if (stack.size() >= 2) {
                    Integer second = stack.pop();
                    Integer first = stack.pop();
                    Integer tmp = 0;
                    if ("+".equals(tokens[i])) {
                        tmp = first + second;

                    } else if ("-".equals(tokens[i])) {
                        tmp = first - second;
                    } else if ("*".equals(tokens[i])) {
                        tmp = first * second;
                    } else if ("/".equals(tokens[i])) {
                        tmp = first / second;
                    }
                    stack.push(tmp);
                }
            }
        }
        return stack.pop();

    }


    public static void main(String[] args) {

        String[] tokens=new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
        Solution solution=new Solution();
        System.out.println(solution.evalRPN(tokens));

    }
}
