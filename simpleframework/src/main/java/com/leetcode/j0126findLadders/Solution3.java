package com.leetcode.j0126findLadders;

import javafx.util.Pair;

import java.util.*;

class Solution3 {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res=new ArrayList<>();
        if(!wordList.contains(endWord)){
            return new ArrayList<>();
        }
        if(!wordList.contains(beginWord)){
            wordList.add(beginWord);
        }
        Integer beginIndex=wordList.indexOf(beginWord);
        Integer endIndex=wordList.indexOf(endWord);
        int[][] ladderVector=new int[wordList.size()][wordList.size()];
        for(int i=0;i<wordList.size();i++){
            for(int j=0;j<wordList.size();j++){
                if(isLadder(i,j,wordList)){
                    ladderVector[i][j]=1;
                    ladderVector[j][i]=1;
                }
            }
        }
        boolean isExist=false;//是否存在符合要求的转换序列
        Map<Integer,Integer> visited=new HashMap<>();
        Map<Integer,List<Integer>> from=new HashMap<>();
        //BFS
        Queue<Pair<Integer, Integer>> queue=new LinkedList<>();
        queue.add(new Pair(beginIndex,1));
        while(!queue.isEmpty()){
            Pair<Integer,Integer> pair=queue.poll();
            Integer key=pair.getKey();
            Integer depth=pair.getValue();
            for(int i=0;i<wordList.size();i++){
                if((!visited.containsKey(i)||visited.get(i)==depth+1)&&ladderVector[i][key]==1){
                    if(i==endIndex){
                        isExist=true;
                    }
                    queue.add(new Pair<>(i,depth+1));
                    visited.put(i,depth+1);
                    from.putIfAbsent(i,new ArrayList<>());
                    if(!from.get(i).contains(key)){
                        from.get(i).add(key);
                    }
                }
            }
        }

        if(isExist){
            Deque path=new LinkedList();
            dfs(wordList,endIndex,beginIndex,path,res,from);
        }
        return res;

    }


    public void dfs(List<String> wordList, Integer begin, Integer end, Deque<String> path,List<List<String>> res, Map<Integer,List<Integer>> from){
        path.addFirst(wordList.get(begin));
        List<Integer> fromnow=from.get(begin);

            for(int i=0;i<fromnow.size();i++){
                if(fromnow.get(i)==end){
                    path.addFirst(wordList.get(end));
                    res.add(new ArrayList<>(path));
                    path.removeFirst();
                    return;
                }
                dfs(wordList,fromnow.get(i),end,path,res,from);
                path.removeFirst();
            }


    }



    public boolean isLadder(int i,int j,List<String> wordList){
        String str1=wordList.get(i);
        String str2=wordList.get(j);
        if(str1.length()!=str2.length()){
            return false;
        }
        int l=str1.length();
        int count=0;
        for(int k=0;k<l;k++){
            if(str1.charAt(k)!=str2.charAt(k)){
                count++;
            }
        }
        return count==1;
    }


    public static void main(String[] args) {
        /*"red"
        "tax"
                ["ted","tex","red","tax","tad","den","rex","pee"]*/
        String beginWord = "red", endWord = "tax";
        String[] strs = new String[]{"ted","tex","red","tax","tad","den","rex","pee"};
        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, strs);
        Solution3 solution = new Solution3();
        List<List<String>> result = solution.findLadders(beginWord, endWord, wordList);
        for (List list : result) {
            System.out.print("[");
            for (Object object : list
            ) {
                System.out.print(object + "->");
            }
            System.out.print("null]");
            System.out.println();
        }
    }

}
