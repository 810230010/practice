package com.huajia.daily.thread.print.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description
 * @Author huajia
 * @Date2021/2/11 13:47
 */
public class PrintABC {
    private static volatile boolean canPrintB = false;
    private static Thread a;
    private static Thread b;
    private static Thread c;

    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            for(int i=0; i<5; i++) {
                LockSupport.park();
                System.out.println("a");
                LockSupport.unpark(b);
            }
        }, "a");

        b = new Thread(() -> {
            for(int i=0; i<5; i++) {
                LockSupport.park();
                System.out.println("b");
                LockSupport.unpark(c);
            }
        }, "b");


        c = new Thread(() -> {
            for(int i=0; i<5; i++) {
                LockSupport.park();
                System.out.println("c");
                LockSupport.unpark(a);
            }
        }, "c");

        a.start();
        b.start();
        c.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockSupport.unpark(a);
    }
}
