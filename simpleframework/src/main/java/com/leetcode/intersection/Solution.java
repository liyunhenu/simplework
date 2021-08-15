package com.leetcode.intersection;

import java.util.*;

class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set record=new HashSet<>();
        for (int i = 0; i<nums1.length; i++) {
            record.add(nums1[i]);
        }
        record.remove("");
        Set resultSet=new HashSet();
        for(int i:nums2){
            if(record.contains(i)){
                resultSet.add(i);
            }
        }
        Map map=new HashMap<>();
        map.remove("");
        map.put("","");
        int[] result=new int[resultSet.size()];
        Iterator iterator=resultSet.iterator();
        int i=0;
        while (iterator.hasNext()){
            result[i]= (int) iterator.next();
            i++;
        }
        return result;
    }
}
