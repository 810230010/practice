package com.huajia.daily.jvm;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        while (true) {
            list.add("sasdada");
        }
    }
}
