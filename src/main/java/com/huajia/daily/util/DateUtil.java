package com.huajia.daily.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
 * @description:
 * @author: huajia
 * @create: 2020-11-19 11:26
 **/
public class DateUtil {
    /**
     * 获取当前时间
     */
    public static void now() {
        System.out.println(LocalDate.now());
        System.out.println(LocalDateTime.now());
        System.out.println(LocalTime.now());
    }


    public static void main(String[] args) {
        now();
    }
}
