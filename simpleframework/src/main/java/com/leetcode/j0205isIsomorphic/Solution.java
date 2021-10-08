package com.leetcode.j0205isIsomorphic;

import java.util.HashMap;
import java.util.Map;

public class Solution {

    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> s2tMap = new HashMap<>();
        Map<Character, Character> t2sMap = new HashMap<>();
        if (s.length() != t.length()) return false;
        for (int i = 0; i < s.length(); i++) {
            Character p1 = s.charAt(i);
            Character p2 = t.charAt(i);
            if (s2tMap.containsKey(p1)) {
                if (s2tMap.get(p1) == p2) {
                    continue;
                } else {
                    return false;
                }
            }
            if (t2sMap.containsKey(p2)) {
                return false;
            }
            s2tMap.put(p1, p2);
            t2sMap.put(p2, p1);
        }
        return true;
    }
}
