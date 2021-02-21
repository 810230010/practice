package com.huajia.daily.thread.count.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description
 * @Author huajia
 * @Date2021/2/10 16:29
 */
public class Count {
    private static volatile int flag = 1;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> { // 打印奇数
            int count = 0;
            while(count < 10) {
                if(flag == 1) {
                    System.out.println("1");
                    flag = 2;
                    count++;
                }else {
                    LockSupport.park();
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {  // 打印偶数
            int count = 0;
            while(count < 10) {
                if(flag == 1) {
                    LockSupport.park();
                }else {
                    System.out.println("2");
                    flag = 1;
                    count++;
                }
            }
        }, "t2");

        t1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
