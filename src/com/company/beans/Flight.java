package com.company.beans;

import com.company.dao.AirplaneDao;
import com.company.dao.AirportDao;
import com.company.dao.RouteDao;


public class Flight {
    private int id;
    private int routeId;
    private int airplaneId;
    private String departureTime;
    private int reservedSeats;
    private int seatPrice;

    public Flight()
    {

    }

    public String flightRoute()
    {
        RouteDao rd = new RouteDao();
        AirportDao ad = new AirportDao();
        Route r = rd.getById(this.routeId);
        Airport from = ad.getById(r.getOriginId());
        Airport to = ad.getById(r.getDestinationId());
        return from.getIataId() + ", " + from.getCity() + " -> " + to.getIataId() + ", " + to.getCity() + " departs: " + departureTime;
    }

    public int remainingSeats()
    {
        AirplaneDao ad = new AirplaneDao();
        Airplane a = ad.getById(airplaneId);
        return a.seats() - reservedSeats;
    }

    public Flight(int id, int routeId, int airplaneId, String departureTime, int reservedSeats, int seatPrice) {
        this.id = id;
        this.routeId = routeId;
        this.airplaneId = airplaneId;
        this.departureTime = departureTime;
        this.reservedSeats = reservedSeats;
        this.seatPrice = seatPrice;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(int airplaneId) {
        this.airplaneId = airplaneId;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(int reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public int getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(int seatPrice) {
        this.seatPrice = seatPrice;
    }
}
