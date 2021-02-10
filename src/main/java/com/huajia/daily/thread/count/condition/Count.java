package com.huajia.daily.thread.count.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author huajia
 * @Date2021/2/10 16:17
 */
public class Count {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition evenCondition = lock.newCondition(); // 偶数条件
    private static final Condition oddCondition = lock.newCondition();  // 奇数条件
    private static volatile int flag = 1;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> { // 打印奇数
            int count = 0;
            while(count < 10) {
                try {
                    lock.lock();
                    if(flag == 1) {
                        System.out.println("1");
                        flag = 2;
                        count++;
                        evenCondition.signal();
                    }else {
                        try {
                            oddCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {  // 打印偶数
            int count = 0;
            while(count < 10) {
                try {
                    lock.lock();
                    if(flag == 1) {
                        try {
                            evenCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("2");
                        flag = 1;
                        count++;
                        oddCondition.signal();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
