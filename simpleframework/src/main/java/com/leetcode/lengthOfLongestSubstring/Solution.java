package com.leetcode.lengthOfLongestSubstring;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int i=0;
        int j=-1;//[i,j]  不重复子串
        int longestLength=-1;
        int[] frequency=new int[256];//存放字母出现的频率
        while (i<s.length()){
            if(j+1<s.length()&&frequency[s.charAt(j+1)]==0){
                frequency[s.charAt(j+1)]=1;
                j++;
            }else{
                frequency[s.charAt(i)]=frequency[s.charAt(i)]-1;
                i++;
            }
            longestLength=Math.max(longestLength,j-i+1);
        }

        if(longestLength==-1){
            return 0;
        }else{
            return longestLength;
        }

    }
}
