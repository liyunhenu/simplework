package com.juge.sort;

public class CountSort implements AbstractSort {
    @Override
    public void sort(int[] arr, int left, int right) throws Exception {
        int min = arr[left];
        int max = arr[right];

        int[] arrCopy = new int[right - left + 1];


        for (int i = left; i <= right; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
            if (arr[i] > max) {
                max = arr[i];
            }
            arrCopy[i - left] = arr[i];
        }

        int[] base = new int[max - min + 1];

        for (int i = left; i <= right; i++) {
            base[arr[i] - min]++;
        }

        for (int i = 1; i < base.length; i++) {
            base[i] += base[i - 1];
        }
        //base算好了次序

        for (int i = right; i >=left; i--) {
            int tmp=base[arrCopy[i] - min];//第多少个元素
            arr[tmp-1] = arrCopy[i];
            tmp--;
        }

    }

    public static void main(String[] args) throws Exception {
        int[] array = {5, 3, 9, 12, 6, 1, 7, 2, 4, 11, 8, 10};
        CountSort countSort=new CountSort();
        countSort.sort(array, 0, array.length - 1);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }
        System.out.println();
    }



}
