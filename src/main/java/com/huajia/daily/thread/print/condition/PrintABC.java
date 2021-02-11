package com.huajia.daily.thread.print.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author huajia
 * @Date2021/2/11 13:22
 */
public class PrintABC {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition aCondition = lock.newCondition();
    private static final Condition bCondition = lock.newCondition();
    private static final Condition cCondition = lock.newCondition();

    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            for(int i=0; i<5; i++) {
                try {
                    lock.lock();
                    try {
                        aCondition.await();
                        System.out.println("a");
                        bCondition.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }, "a");

        Thread b = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    lock.lock();
                    try {
                        bCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("b");
                    cCondition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }, "b");

        Thread c = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    lock.lock();
                    try {
                        cCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("c");
                    aCondition.signal();
                } finally {
                    lock.unlock();
                }
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
        try {
            lock.lock();
            aCondition.signal();
        } finally {
            lock.unlock();
        }
    }
}
