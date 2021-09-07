package com.leetcode.containsNearbyDuplicate;

import java.util.HashSet;
import java.util.Set;

public class Solution {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        int length=nums.length;
        int i=0;
        int j=0;
        Set<Integer> setWindow=new HashSet<>();
        while (j<length){
            for ( ; j <= i+k&&j<length; j++) {
               if(setWindow.contains(nums[j])){
                   return true;
               }else {
                  setWindow.add(nums[j]);
               }
            }
            setWindow.remove(nums[i++]);
        }
        return false;
    }
}
