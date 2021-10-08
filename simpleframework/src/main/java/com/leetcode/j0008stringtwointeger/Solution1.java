package com.leetcode.j0008stringtwointeger;

import java.util.HashMap;
import java.util.Map;

public class Solution1 {

    public static int myAtoi(String s) {

        Automachine automachine=new Automachine();


        for(int i=0;i<s.length();i++){
            automachine.process(s.charAt(i));
        }

        return automachine.getValue();

    }

    public static class Automachine{

        public String currentStat="start";//当前状态
        int signed=1;//符号
        long ans;//当前绝对值
        Map<String,String[]> table=new HashMap(){
            {
                put("start", new String[]{"start", "signal", "numbers", "end"});
                put("signal",new String[]{"end","end","numbers","end"});
                put("numbers",new String[]{"end","end","numbers","end"});
                put("end",new String[]{"end","end","end","end"});
            }
        };

        public  void process(char input){

            currentStat=table.get(currentStat)[getCol(input)];//状态转移
            if("numbers".equals(currentStat)){
                ans=ans*10+(input-'0');
                ans=(signed==1)?Math.min((long)Integer.MAX_VALUE,ans):Math.min(-(long)Integer.MIN_VALUE,ans);

            }else if("signal".equals(currentStat)){
                signed=input=='+'?1:-1;
            }

        }

        private int getCol(char input) {
            if(input==' '){
                return 0;
            }else if(input=='+'||input=='-'){
                return 1;
            }else if(input>='0'&&input<='9'){
                return 2;
            }else {
                return 3;
            }
        }

        public int getValue(){
            return signed*(int)ans;
        }


    }

    public static void main(String[] args) {
        String input=new String("   -42");
        System.out.println(myAtoi(input));
    }
}
