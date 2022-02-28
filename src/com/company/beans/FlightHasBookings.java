package com.company.beans;

public class FlightHasBookings {
    private int bookingId;
    private int flightId;

    public FlightHasBookings(int bookingId, int flightId) {
        this.bookingId = bookingId;
        this.flightId = flightId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
}
