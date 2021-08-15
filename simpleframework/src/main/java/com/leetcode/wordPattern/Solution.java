package com.leetcode.wordPattern;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] patterns = s.split(" ");//s字符串去掉空格形成的数组
        if (pattern.length() != patterns.length) {
            return false;
        }
        Map<Character, String> characterStringMap = new HashMap<>();
        Map<String, Character> stringCharacterMap = new HashMap<>();
        for (int i = 0; i < patterns.length; i++) {
            if (stringCharacterMap.containsKey(patterns[i])) {
                if (stringCharacterMap.get(patterns[i]).equals(pattern.charAt(i))) {
                    continue;
                } else {
                    return false;
                }
            } else {
                if (characterStringMap.containsKey(pattern.charAt(i))) {
                   /* if (characterStringMap.get(pattern.charAt(i)).equals(patterns[i])) {
                        continue;
                    } else {
                        return false;
                    }*/
                    return false;
                } else {
                    //字符及字符串均是新出现的
                    characterStringMap.put(pattern.charAt(i), patterns[i]);
                    stringCharacterMap.put(patterns[i], pattern.charAt(i));
                }
            }
        }
        return true;
    }
}
