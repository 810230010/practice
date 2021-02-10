package com.huajia.daily.thread.ticket.lock;


public class Audience implements Runnable{
    private TicketOffice ticketOffice;

    public Audience(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void buy() {
        this.ticketOffice.sell();
    }

    @Override
    public void run() {
        buy();
    }
}
