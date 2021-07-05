package com.leetcode.validutf8;

public class Solution {
    public boolean validUtf8(int[] data) {
        Boolean valid = false;
        //判断0前面有几个字节
        int MASK = 1 << 7;
        for (int i = 0; i < data.length; i++) {
            int nBit = 0;
            while (MASK > 1) {
                if ((MASK & data[i]) != 0) {
                    nBit++;
                    MASK = MASK >> 1;//
                } else {
                    break;
                }
            }
            if (nBit == 0) {
                continue;
            } else if (nBit > 4) {
                return false;//超过四个字节，一定是错误的
            }
            if (i + nBit - 1 >= data.length) {
                return false;
            }
            //字节介于1-4个的，判断后面nbit-1个整数，是不是都是10开头的，如果不满足就是错误的
            int mask1 = 1 << 7;
            int mask2 = 1 << 6;
            for (int j = i + 1; j < i + nBit; j++) {
                if ((data[j] & mask1) !=0 && (data[j] & mask2) ==0) {

                } else {
                    return false;
                }
            }
            i=i+nBit-1;


        }


        return true;
    }
}
