package com.leetcode.j0126findLadders;

import javafx.util.Pair;

import java.util.*;

public class Solution {

    //dfs重构树  每层有几个元素   每个元素从哪几个元素获得
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Map<Integer, List<Integer>> from = new HashMap<>();
        boolean isExist = false;

        if (!wordList.contains(endWord)) {
            return res;
        }
        if (!wordList.contains(beginWord)) {
            wordList.add(beginWord);
        }
        int k = wordList.indexOf(beginWord);
        int length = wordList.size();
        int[][] ladderVector = new int[length][length];
        for (int i = 0; i < wordList.size(); i++) {
            for (int j = i; j < wordList.size(); j++) {
                if (isLadder(wordList.get(i), wordList.get(j))) {
                    ladderVector[i][j] = ladderVector[j][i] = 1;
                } else {
                    ladderVector[i][j] = ladderVector[j][i] = 0;
                }
            }
        }
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(k, 1));
        Map<Integer, Integer> visited = new HashMap<>();
        visited.put(k, 1);
        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            int position = (int) pair.getKey();
            int depth = (int) pair.getValue();
            for (int i = 0; i < wordList.size(); i++) {

                if ((!visited.containsKey(i) || visited.get(i) >= depth + 1) && ladderVector[position][i] == 1) {

                    if (wordList.get(i) == endWord) {
                        isExist = true;
                    }
                    queue.offer(new Pair<>(i, depth + 1));
                    visited.put(i, depth + 1);
                    from.putIfAbsent(i, new ArrayList<>());
                    from.get(i).add(position);
                }
            }

        }
        if (isExist) {
            Deque<String> path = new LinkedList<>();
            dfs(wordList, endWord, beginWord, path, from, res);
        }


        return res;
    }

    private void dfs(List<String> wordList, String beginWord, String endWord, Deque<String> path, Map<Integer, List<Integer>> from, List<List<String>> res) {
        int beginIndex = wordList.indexOf(beginWord);
        int endIndex = wordList.indexOf(endWord);
        path.addFirst(beginWord);
        List fromNow = from.get(beginIndex);
        for (int i = 0; i < fromNow.size(); i++) {
            if ((int) fromNow.get(i) == endIndex) {
                path.addFirst(endWord);
                res.add(new ArrayList<>(path));//当前路径找到了
                path.removeFirst();
                return;
            }
            dfs(wordList, wordList.get((int) fromNow.get(i)), endWord, path, from, res);
            path.removeFirst();
        }
    }

    public boolean isLadder(String str, String str1) {
        if (str.length() != str1.length()) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != str1.charAt(i)) {
                count++;
            }
        }
        return count == 1;
    }

    public static void main(String[] args) {
        String beginWord = "red", endWord = "tax";
        String[] strs = new String[]{"ted","tex","red","tax","tad","den","rex","pee"};
        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, strs);
        Solution solution = new Solution();
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
