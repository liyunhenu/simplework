package com.leetcode.j0127ladderLength;

import java.util.List;

public class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int length=wordList.size();
        int[][] ladderVector=new int[length][length];
        for (int i = 0; i < wordList.size(); i++) {
            for (int j = i; j < wordList.size(); j++) {
                if(isLadder(wordList.get(i),wordList.get(j))){
                    ladderVector[i][j]=ladderVector[j][i]=1;
                }else {
                    ladderVector[i][j]=ladderVector[j][i]=0;
                }
            }
        }
        //Queue<Pair<Integer,Integer>>
        return 0;
    }


    public boolean isLadder(String str,String str1){
        if(str.length()!=str1.length()){return false;}
        int count=0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)!=str1.charAt(i)){count++;}
        }
        return count==1;
    }


}
