package com.huajia.daily.algorithm;

import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = {1,6,5,21,2};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        for(int i=0; i<arr.length-1; i++) {
            int min = arr[i];
            int minIndex = i;
            for(int j=i+1; j<arr.length; j++) {
                if(arr[j] < min) {
                    minIndex = j;
                    min = arr[j];
                }
            }
            if(i != minIndex) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
}
