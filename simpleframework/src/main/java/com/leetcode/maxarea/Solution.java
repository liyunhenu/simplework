package com.leetcode.maxarea;

class Solution {
    public int maxArea(int[] height) {
        int maxArea=0;
        for(int i=0;i<height.length;i++){
            int tmp=currentArea(height,i,maxArea);
            if(tmp>=maxArea){
                maxArea=tmp;
            }
        }
        return maxArea;
    }

    public int currentArea(int[] height,int p,int maxArea){
        if(height[p]*p<=maxArea) return maxArea;
        int value=0;
        for(int i=0;i<p;i++){
            int tmp=(p-i)*Math.min(height[i],height[p]);
            if(tmp>=value) value=tmp;
        }
        return value;
    }
}
