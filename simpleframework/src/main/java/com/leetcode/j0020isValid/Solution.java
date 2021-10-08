package com.leetcode.j0020isValid;

import java.util.Stack;

public class Solution {

    public boolean isValid(String s ){
        Stack<Character> stack=new Stack();
        for (int i = 0; i < s.length(); i++) {
            if(isLeft(s.charAt(i))){
                stack.push(s.charAt(i));
            }else {
                if(stack.size()<1){
                    return false;
                }
                if(s.charAt(i)==')'){
                    if(stack.pop()=='('){
                        continue;
                    }else {
                        return false;
                    }
                }else if(s.charAt(i)==']'){
                    if(stack.pop()=='['){
                        continue;
                    }else {
                        return false;
                    }

                }else if(s.charAt(i)=='}'){
                    if(stack.pop()=='{'){
                        continue;
                    }else {
                        return false;
                    }
                }
            }
        }
        return stack.empty();
    }

    private boolean isLeft(char charAt) {
        return charAt=='('||charAt=='['||charAt=='{';
    }


    public static void main(String[] args) {
        String input="[[]]]]";
        Solution solution=new Solution();
        System.out.println(solution.isValid(input));
    }
}
