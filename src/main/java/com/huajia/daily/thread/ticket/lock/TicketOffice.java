package com.huajia.daily.thread.ticket.lock;


import java.util.concurrent.locks.ReentrantLock;

/**
 * 售票处
 */
public class TicketOffice {
    private int totalTicketCount = 100;

    private final ReentrantLock reentrantLock = new ReentrantLock();

    public void sell() {
        try {
            reentrantLock.lock();

            if(totalTicketCount > 0) {
                totalTicketCount--;
                System.out.println("售票窗口提示: 卖出成功，当前剩余票数：" + totalTicketCount);
            }else {
                System.err.println("售票窗口提示：票已卖完");
            }
        }finally {
            reentrantLock.unlock();
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
