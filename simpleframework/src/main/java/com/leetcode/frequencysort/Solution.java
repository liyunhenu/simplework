package com.leetcode.frequencysort;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public String frequencySort(String s) {
        Map<Character,Integer> map=new HashMap<>();
        int maxFrequency=1;
        for (int i = 0; i < s.length(); i++) {
            char tmp=s.charAt(i);
            if(map.containsKey(tmp)){
                int frequency=map.get(tmp);
                ++frequency;
                if(maxFrequency<frequency){
                    maxFrequency=frequency;
                }
                map.put(tmp,frequency);
            }else {
                map.put(tmp,1);
            }
        }
        StringBuilder[] stringBuilders=new StringBuilder[maxFrequency+1];
        for(int i=0;i<=maxFrequency;i++){
            stringBuilders[i]=new StringBuilder();
        }
        for(Character character:map.keySet()){
            Integer frequency=map.get(character);
            stringBuilders[frequency].append(character);
        }
        StringBuilder result=new StringBuilder();
        for (int i = maxFrequency; i >=1 ; i--) {
            for(int k=0;k<stringBuilders[i].length();k++){
                for(int j=0;j<i;j++){
                    result.append(stringBuilders[i].charAt(k));
                }
            }
        }
        return result.toString();

    }
}
