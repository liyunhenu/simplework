package com.leetcode.isAnagram;


import java.util.HashMap;
import java.util.Map;

public class Solution {

    public boolean isAnagram(String s, String t) {
        String recordString = s;
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Integer> record = new HashMap<>();
        for (int i = 0; i < recordString.length(); i++) {
            Character tmp=recordString.charAt(i);
            record.put(tmp,record.getOrDefault(tmp,0)+1);

        }

        for (int i = 0; i < t.length(); i++) {
            Character tmp=t.charAt(i);
            if (record.containsKey(t.charAt(i))) {
                record.put(tmp,record.getOrDefault(tmp,0)-1);
            } else {
                return false;
            }
        }

        return true;
    }
}
