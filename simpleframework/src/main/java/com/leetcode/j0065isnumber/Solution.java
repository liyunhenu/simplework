package com.leetcode.j0065isnumber;

import java.util.HashMap;
import java.util.Map;

class Solution {

    public static final Map<Integer,int[]> DFA=new HashMap<>();

    static{
        DFA.put(0, new int[]{1, 2, 10, 4, 10});//开始状态
        DFA.put(1, new int[]{10, 2, 10, 4, 10});//+-号状态
        DFA.put(2, new int[]{10, 10, 10, 4, 10});//.状态前面没数字
        DFA.put(3, new int[]{10, 10, 5, 4, 10});//.状态前面有数字
        DFA.put(4, new int[]{10, 3, 5, 4, 10});//纯数字
        DFA.put(5, new int[]{6, 10, 10, 4, 10});//eE状态
        DFA.put(6,new int[]{10,10,10,4,10});//第二次出现+-号的时候
        DFA.put(10, new int[]{10, 10, 10, 10, 10});//ERROR状态
    }


    int signaled=0;//是否出现过正负号
    Boolean doted=false;//是否出现过小数点
    Boolean natured=false;//是否出现过自然数符号


    int currStat=0;

    public  boolean isNumber(String s) {

        for(int i=0; i<s.length();i++){
            int[] tmp=DFA.get(currStat);
            currStat=tmp[getType(s.charAt(i))];
            if(10==currStat){
                return false;
            }

        }
        if(10==currStat||2==currStat||5==currStat||6==currStat||1==currStat){
            return false;
        }else{
            return true;
        }


    }


    public  int getType(char input){
        if(input=='+'||input=='-'){
            if(signaled==1&&currStat==5){//第二次出现+-号
                signaled=2;
                return 0;
            }else if(signaled>1){
                return 4;

            }else {
                signaled=1;
                return 0;
            }

        }else if('.'==input){
            if(natured){
                return 4;//出现过e/E 就不能再出现小数点
            }
            if(doted){
                return 4;
            }else{
                doted=true;
                return 1;
            }

        }else if('e'==input||'E'==input){
            if(natured){
                return 4;
            }else{
                natured=true;
                return 2;
            }

        }else if(input>='0'&&input<='9'){
            return 3;
        }else{
            return 4;
        }
    }


}
