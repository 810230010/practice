package com.huajia.daily.algorithm;

import com.huajia.daily.reflection.User;

import java.util.Arrays;

public class InsertSort {
    public static void main(String[] args) {
        int[] arr = {1,7,2,5,1};
        System.out.println(Arrays.toString(sort(arr)));
    }

    private static int[] sort(int[] arr) {
        for(int i=1; i<arr.length; i++) {
            int index = i;
            int temp = arr[i];
            while(index > 0 && temp <= arr[index - 1]) {
                arr[index] = arr[index - 1];
                index--;
            }

            arr[index] = temp;
        }
        return arr;
    }
}
