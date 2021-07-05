package com.leetcode.frequencysort;

import java.util.*;

public class Solution1 {

    public String frequencySort(String s) {
        Map<Character,Integer> map=new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
           if(map.containsKey(s.charAt(i))){
               Integer frequency=map.get(s.charAt(i));
               map.put(s.charAt(i),++frequency);
           }else {
               map.put(s.charAt(i),1);
           }
        }
        List<Character> list=new ArrayList<>(map.keySet());
        Collections.sort(list, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                if(map.get(o1)==map.get(o2)) return 0;
                return map.get(o1)>map.get(o2)?-1:1;
            }
        });
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            int frequency=map.get(list.get(i));
            for (int j = 0; j < frequency; j++) {
                stringBuilder.append(list.get(i));
            }

        }
        return stringBuilder.toString();

    }
}
