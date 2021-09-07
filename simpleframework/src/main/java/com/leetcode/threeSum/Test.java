package com.leetcode.threeSum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        int[] nums=new int[]{-1,0,1,2,-1,-4,-2,-3,3,0,4};
        Solution solution=new Solution();
        List<List<Integer>> result=solution.threeSum(nums);
        Iterator iterable=result.iterator();
        StringBuffer sb=new StringBuffer("[");
        while (iterable.hasNext()){
            List<Integer> tmp=(List<Integer>) iterable.next();
            sb.append("[");
            sb.append(tmp.get(0)).append(",");
            sb.append(tmp.get(1)).append(",");
            sb.append(tmp.get(2)).append("],");
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
    //error
    /*[[-4,0,4],[-3,-1,4],[-3,0,3],[-2,-1,3],[-2,0,2],[-1,-1,2],[-1,0,1]]*/
    /*[[-4,0,4],[-4,1,3],[-3,-1,4],[-3,0,3],[-3,1,2],[-2,-1,3],[-2,0,2],[-1,-1,2],[-1,0,1]]*/
}
