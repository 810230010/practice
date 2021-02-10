package com.huajia.daily.thread.print.wait;

/**
 * @Description
 * @Author huajia
 * @Date2021/2/10 17:24
 */
public class PrintABC {
    private static volatile boolean canPrintB = false;

    private static volatile boolean canPrintC = false;

    private static volatile boolean canPrintA = true;

    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            for(int i=0; i<5; i++) {
                synchronized (lock) {
                    while(!canPrintA) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("a");
                    canPrintA = false;
                    canPrintB = true;
                    lock.notifyAll();
                }
            }
        }, "A");

        Thread b = new Thread(() -> {
            for(int i=0; i<5; i++) {
                synchronized (lock) {
                    while(!canPrintB) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("b");
                    canPrintB = false;
                    canPrintC = true;
                    lock.notifyAll();
                }
            }
        }, "B");

        Thread c = new Thread(() -> {
            for(int i=0; i<5; i++) {
                synchronized (lock) {
                    while(!canPrintC) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("c");
                    canPrintC = false;
                    canPrintA = true;
                    lock.notifyAll();
                }
            }
        }, "C");

        a.start();
        b.start();
        c.start();
    }
}
