package com.imooc.sort;

import java.util.Arrays;
import java.util.Random;

public class QuickSortImp {


    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 6, 5, 3, 2, 8, 1};
        QuickSortImp.sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }


    /**
     * 不推荐，太复杂
     * 填坑法实现快排，坑的位置就是下次被pivot分割规则确定的位置，左右轮换，
     *
     * @param arr
     * @param begin
     * @param end
     * @return
     */
    private static int findPivot1(int[] arr, int begin, int end) {

        Random random = new Random();
        int randomIndex = begin + random.nextInt(end - begin + 1);
        swap(arr, begin, randomIndex);

        int pivot = arr[begin];

        int left = begin;
        int right = end;
        int index = begin;
        while (left <= right) {
            while (left <= right) {
                if (arr[right] > pivot) {
                    right--;
                } else {
                    //
                    arr[index] = arr[right];//填入现有的坑中
                    index = right;//新坑的位置
                    left++;
                    break;
                }
            }
            while (left <= right) {
                if (arr[left] < pivot) {
                    left++;
                } else {
                    arr[index] = arr[left];//填入坑中
                    index = left;
                    right--;
                    break;
                }
            }
            arr[index] = pivot;
        }

        return index;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public static void sort(int[] arr, int begin, int end) {
        if (begin >= end) {
            return;
        }
        int pivot = findPivot(arr, begin, end);
        sort(arr, begin, pivot - 1);
        sort(arr, pivot + 1, end);
    }

    private static int findPivot(int[] arr, int left, int right) {

        Random random = new Random();
        int randomIndex = left + random.nextInt(right - left + 1);
        swap(arr, right, randomIndex);
        int i = left;
        int j = right - 1;
        int pivot = arr[right];
        while (i <= j) {
            if (arr[i] <=pivot) {
                i++;
            } else {
                swap(arr, i, j);
                j--;
            }
        }
        swap(arr, i, right);//基准pivot选在右边，应该跟大的数交换，i已经超过了j
        return i;
    }
}
