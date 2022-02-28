package com.company.beans;

public class FlightBooking {
    private int flightId;
    private int bookingId;


    public FlightBooking(int flightId, int bookingId) {
        this.flightId = flightId;
        this.bookingId = bookingId;
    }

    public FlightBooking()
    {

    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
