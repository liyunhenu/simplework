package com.imooc.sort;

import java.util.Arrays;

public class HeapSortImp {

    public static void main(String[] args) {
        int[] array = new int[]{95, 94, 91, 98, 99, 90, 99, 93, 91, 92,89,30,22,22,33};
        heapSort(array);
        System.out.println(Arrays.toString(array));
    }

    private static void heapSort(int[] array) {
        //构建最大堆
        int length = array.length;
        int d = (int) Math.floor(length / 2);
        for (int i = d; i >=0; i--) {
            heapfy(array, i, length);
        }
        //交换最大堆的堆顶元素跟堆尾部元素
        for (int i = array.length-1; i>0 ; i--) {
            swap(array,0,i);
            length--;
            heapfy(array,0,length);
        }
        //保持堆
    }


    public static void heapfy(int[] arr, int i, int len) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int max = i;
        if (left < len && arr[max] < arr[left]) {
            max = left;
        }
        if (right < len && arr[max] < arr[right]) {
            max = right;
        }
        if (max != i) {
            swap(arr, i, max);
            heapfy(arr, max, len);
        }

    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
