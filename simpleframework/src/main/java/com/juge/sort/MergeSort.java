package com.juge.sort;

public class MergeSort implements AbstractSort {


    public void sort(int[] arr, int left, int right) throws Exception {
        if (left >= right) {
            return;
        }
        int pivot = (left + right) / 2;
        sort(arr, left, pivot);
        sort(arr, pivot + 1, right);
        merge(arr, left, right, pivot);//

    }


    public void merge(int[] arr, int left, int right, int pivot) throws Exception {

        if(arr.length < 2 || left > right || pivot >= right)
            throw new Exception("Para error.");

        int leftLength = pivot - left + 1;
        int rightLength = right - pivot;

        int[] leftArr = new int[leftLength + 1];

        int[] rightArr = new int[rightLength + 1];

        for (int i = 0; i < leftLength; i++) {
            leftArr[i] = arr[left + i];
        }
        leftArr[leftLength] = Integer.MAX_VALUE;
        for (int i = 0; i < rightLength; i++) {
            rightArr[i] = arr[pivot + 1 + i];
        }
        rightArr[rightLength] = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        for (int k = left; k <= right; k++) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
        }


    }


    public static void main(String[] args) throws Exception {
        MergeSort mergeSort = new MergeSort();
        int[] array = {5, 3, 9, 12, 6, 1, 7, 2, 4, 11, 8, 10};
        mergeSort.sort(array, 0, array.length - 1);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }
        System.out.println();

    }


}
