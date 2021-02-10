package com.huajia.daily.thread.ticket.blockingqueue;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 售票处
 */
public class TicketOffice {
    private int totalTicketCount = 100;

    private final BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(100);

    public TicketOffice() {
        for(int i=1; i<=100; i++) {
            blockingQueue.add(1);
        }
    }

    public void sell() {
        try {
            Integer ticket = blockingQueue.take();
            System.out.println("售票窗口提示: 卖出成功，当前剩余票数：" + blockingQueue.size());
        } catch (InterruptedException e) {
            System.err.println("售票窗口提示：票已卖完");
        }
    }

    public static void main(String[] args) {
        TicketOffice ticketOffice = new TicketOffice();
        for(int i=0; i<200; i++) {
            Thread thread = new Thread(new Audience(ticketOffice));
            thread.start();
        }
    }
}
