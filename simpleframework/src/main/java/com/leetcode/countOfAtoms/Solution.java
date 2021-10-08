package com.leetcode.countOfAtoms;

import java.util.*;

public class Solution {
    public String countOfAtoms(String formula) {
        Map<String,Integer> map=new TreeMap<String,Integer>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Character p1=o1.toLowerCase().charAt(0);
                Character p2=o2.toLowerCase().charAt(0);
                return Character.compare(p1,p1);
            }
        });
        Map<Character,Integer> k=new HashMap<>();
        Integer pre=0;
        StringBuilder sb=new StringBuilder();
        int i=0;
        int j=formula.length()-1;
        int count=0;
        while (i<=j){
            if(formula.charAt(i)!='(')
            {
                i++;
            }else {
                if(formula.charAt(j)!=')'){
                    j--;
                }else {
                    count++;
                    i=i+1;
                    j=j-1;
                    continue;
                }

            }
        }
        int p=j;//中间位置 j右边是括号或者最后一个元素
        i=j;
        while (i>=0&&j<=formula.length()-1){
            Character tmp=formula.charAt(i);
            if(tmp.equals('(')){
                j=j+2;
                int value=Integer.valueOf(formula.charAt(j));
                Iterator<String> iterator=map.keySet().iterator();
                while (iterator.hasNext()){
                    String s=iterator.next();
                    Integer value1=value*map.get(s);
                    map.put(s,value1);
                }
            }else if(tmp>'1'&&tmp<='9'){
                i=i-1;
                Character tmp1=formula.charAt(i);
                String tmp2="";
                if(tmp1>='a'&&tmp1<='z'){
                    tmp2=formula.substring(i-1,i);
                    i=i-2;
                }else {
                     tmp2=formula.substring(i,i);
                     i=i-1;
                }
                if (map.containsKey(tmp2)){
                    map.put(tmp2,map.get(tmp2)+1);
                }else {
                    map.put(tmp2,1);
                }
            }else if(tmp>='a'&&tmp<='z'){
                String tmp2=formula.substring(i-1,i);
                if (map.containsKey(tmp2)){
                    map.put(tmp2,map.get(tmp2)+1);
                }else {
                    map.put(tmp2,1);
                }
                i=i-2;
            }else {
                String tmp2=formula.substring(i,i);
                if (map.containsKey(tmp2)){
                    map.put(tmp2,map.get(tmp2)+1);
                }else {
                    map.put(tmp2,1);
                }
                i=i-1;
            }

        }


        /*for(int i=0;i<formula.length();i++){
            Character tmp=formula.charAt(i);
            if()
        }*/

        return "";
    }

}
