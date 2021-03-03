package com.huajia.daily.algorithm;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[]{1,7,3,8,3,2};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr) {
        if(arr == null || arr.length <= 1) return;

        sort(arr, 0, arr.length-1);
    }

    public static void sort(int[] arr, int left, int right) {
        if(left > right) {
            return;
        }

        int base = arr[left];
        int i = left;
        int j = right;
        while(i != j) {
            while(arr[j] >= base && i < j) {
                j--;
            }

            while(arr[i] <= base && i < j) {
                i++;
            }

            if(i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // 把基准数放到中间
        arr[left] = arr[i];
        arr[i] = base;

        sort(arr, left, i-1);
        sort(arr, i+1, right);
    }
}
