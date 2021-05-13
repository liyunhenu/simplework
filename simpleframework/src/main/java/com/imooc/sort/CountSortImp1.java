package com.imooc.sort;

import java.util.Arrays;

public class CountSortImp1 {


    public static int[] sort(int[] arr) {

        //计算最大值，最小值
        int max = arr[0];
        int min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) max = arr[i];
            if (min > arr[i]) min = arr[i];
        }
        //初始化max-min+1大小的数组
        int[] countArray = new int[max - min + 1];
        //统计原数组中各个值出现的次数
        for (int i = 0; i < arr.length; i++) {
            countArray[arr[i] - min]++;
        }
        //算出次序
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }
        //从后往前依次。从copy原数组中，从次序表中查出顺序，放到arr的对应位置，次序表减1
        int[] newArr = Arrays.copyOf(arr, arr.length);
        for (int i = arr.length - 1; i >= 0; i--) {
            newArr[countArray[arr[i]-min]-1]=arr[i];
            countArray[arr[i] - min]--;
        }
        return newArr;
    }

    public static void main(String[] args) {
        //int[] array = {5, 3, 9, 12, 6, 1, 7, 2, 4, 11, 8, 10};
        int[] array = new int[]{95, 94, 91, 98, 99, 90, 99, 93, 91, 92};
        int[] newarr=sort(array);
        System.out.println(Arrays.toString(newarr));
    }
}


