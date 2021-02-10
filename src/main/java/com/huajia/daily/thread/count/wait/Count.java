package com.huajia.daily.thread.count.wait;

/**
 * @Description
 * @Author huajia
 * @Date2021/2/10 15:50
 */
public class Count {
    private static final Object mutex = new Object();
    private static volatile int flag = 1;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for(int i=0; i<10; i++) {
                synchronized (mutex) {
                    while(flag == 2) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("1");
                    flag = 2;
                    mutex.notify();
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for(int i=0; i<10; i++) {
                synchronized (mutex) {
                    while(flag == 1) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("2");
                    flag = 1;
                    mutex.notify();
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
