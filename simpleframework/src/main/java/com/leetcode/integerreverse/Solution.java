package com.leetcode.integerreverse;

import java.util.Stack;

public class Solution {
    /**
     * 自己写的好low，还不满足要求
     * @param x
     * @return
     */
    public static int reverse(int x) {
        int result=0;
        if(Math.abs(x)<10){
           return x;
        }
        Stack stack=new Stack();
        while(Math.abs(x)>0){
            int tmp=x%10;
            stack.push(tmp);
            x=x/10;
        }
        int worth=1;
        while (!stack.empty()){
            int tmp= (int) stack.pop();
            result+=tmp*worth;
            worth=worth*10;
            if(result>=Integer.MAX_VALUE||result<=Integer.MIN_VALUE){
                result=0;
                break;
            }

        }
        return result;
    }

    public static void main(String[] args) {
        int value=0;
        int result=reverse(value);
        System.out.println(result);
    }
}
