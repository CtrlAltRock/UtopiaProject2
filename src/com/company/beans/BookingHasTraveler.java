package com.company.beans;

public class BookingHasTraveler {
    private int bookingId;
    private int travelerId;

    public BookingHasTraveler(int bookingId, int travelerId) {
        this.bookingId = bookingId;
        this.travelerId = travelerId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(int travelerId) {
        this.travelerId = travelerId;
    }
}
