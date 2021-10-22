package com.leetcode.j0279numSquares;

import javafx.util.Pair;

import java.util.*;

public class Solution {

    public int numSquares(int n) {
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(new Pair(n, 0));
        int[] tmp=new int[n+1];
        tmp[n]=1;
        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            Integer val = (Integer) pair.getKey();
            Integer depth = (Integer) pair.getValue();
            for (int i = 1; i * i <= val; i++) {
                Integer newKey=val-i*i;
                if(newKey==0){return depth+1;}
                if(tmp[newKey]==0){
                    queue.add(new Pair<>(newKey,depth+1));
                    tmp[newKey]=1;
                }
            }

        }
        return n;
    }

    public static void main(String[] args) {
        Integer n=13;
        Solution solution=new Solution();
        System.out.println(solution.numSquares(n));
    }




}
