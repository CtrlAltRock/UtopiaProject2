package com.company.beans;

public class BookingAgent {
    private int bookingId;
    private int agent_id;

    public BookingAgent(int bookingId, int agent_id) {
        this.bookingId = bookingId;
        this.agent_id = agent_id;
    }

    public BookingAgent()
    {

    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }
}
