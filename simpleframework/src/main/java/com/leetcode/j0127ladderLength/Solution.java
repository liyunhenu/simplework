package com.leetcode.j0127ladderLength;

import javafx.util.Pair;

import java.util.*;

public class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return 0;
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
        int[] visited = new int[wordList.size()];
        visited[k] = 1;
        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            int position = (int) pair.getKey();
            int depth = (int) pair.getValue();
            for (int i = 0; i < wordList.size(); i++) {
                if (visited[i] != 1 && ladderVector[position][i] == 1) {
                    if (wordList.get(i) == endWord) {
                        return depth + 1;
                    } else {
                        queue.offer(new Pair<>(i, depth + 1));
                        visited[i] = 1;
                    }
                }
            }

        }
        return 0;
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
        String beginWord = "hit", endWord = "cog";
        String[] strs = new String[]{"hot", "dot", "dog", "lot", "log", "cog"};
        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, strs);
        Solution solution = new Solution();
        System.out.println(solution.ladderLength(beginWord, endWord, wordList));

    }


}
