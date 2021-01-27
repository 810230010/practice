package com.huajia.daily.algorithm;

import java.util.Objects;

public class ReverseInteger {
    public int reverse(int x) {
        boolean negative = x < 0;
        String s = negative ? (-1 * x) + "" : x + "";
        StringBuilder sb = new StringBuilder(s);
        String reverse = sb.reverse().toString();
        try {
            return negative ? -1 * Integer.parseInt(reverse) : Integer.parseInt(reverse);
        }catch(Exception e) {
            return 0;
        }
    }
}
