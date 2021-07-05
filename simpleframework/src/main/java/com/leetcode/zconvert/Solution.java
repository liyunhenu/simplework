package com.leetcode.zconvert;

class Solution {
    public String convert(String s, int numRows) {
        if(numRows==1) return s;
        StringBuilder[] stringBuilders=new StringBuilder[numRows];
        for(int i=0;i<numRows;i++){
            stringBuilders[i]=new StringBuilder();
        }
        for(int i=0;i<s.length();i++){
            stringBuilders[juge(numRows,i)].append(s.charAt(i));
        }
        StringBuilder result=new StringBuilder();
        for(int i=0;i<numRows;i++){
            result.append(stringBuilders[i]);
        }
        return result.toString();
    }
    public int juge(int numRows,int i){
        int p=2*numRows-2;
        int tmp=Math.abs((i)%p-(numRows-1));
        int yu=(numRows-1)-tmp;
        return yu;

    }
}
