package com.leetcode.countPairs;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int countPairs(int[] deliciousness) {
        Integer MOD=1000000007;
        Integer count=0;
        Map<Integer,Integer> map=new HashMap<>();
        Integer maxNum=0;
        for (int i = 0; i < deliciousness.length; i++) {
            int val=deliciousness[i];
            maxNum=Math.max(val,maxNum);
            for (int j = 1; j <=maxNum*2; j<<=1) {
                int value=map.getOrDefault(j-val,0);
                //int value1=map.getOrDefault(val,0)+1;
                count=(count+value)%MOD;
            }
            map.put(val,map.getOrDefault(val,0)+1);
        }

        return count;

    }
}
