package com.leetcode.minWindow;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public String minWindow(String s, String t) {
        int i = 0;
        int j = -1;//[i,j]包含t
        int windowLength = s.length()+1;
        String result = "";
        Set tset = new HashSet<Character>();
        int[] tFrequency = new int[256];
        for (int k = 0; k < t.length(); k++) {
            Character tmp = t.charAt(k);
            tFrequency[tmp]++;
            tset.add(tmp);
        }
        int[] sFrequency = new int[256];
        while (i < s.length()) {
            if ((j + 1) < s.length() && !containsString(sFrequency, tFrequency, tset)) {
                sFrequency[s.charAt(j + 1)]++;
                j++;
            } else {
                if (containsString(sFrequency, tFrequency, tset)) {
                    sFrequency[s.charAt(i)]--;
                    if (windowLength > (j - i + 1)) {
                        result = s.substring(i, j + 1);
                        windowLength = j - i + 1;
                    }
                    i++;
                }else{
                  break;
                }
            }
        }
        if (windowLength == s.length()+1) {
            return "";
        }
        return result;
    }

    public boolean containsString(int[] sFrequency, int[] tFrequency, Set<Character> set) {
        for (Character tmp : set) {
            if (sFrequency[tmp] < tFrequency[tmp]) {
                return false;
            }
        }
        return true;
    }


}
