package com.company.dao;

import com.company.beans.Flight;
import com.company.beans.FlightBooking;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FlightBookingDao implements AbstractDao<FlightBooking>{
    @Override
    public Optional<FlightBooking> get(long id) {
        return Optional.empty();
    }

    public String dbName;

    public FlightBookingDao()
    {
        this.dbName = "flight_bookings";
    }

    public FlightBooking parseFBSQL(ResultSet rs)
    {
        FlightBooking fb = new FlightBooking();
        try
        {
            fb.setBookingId(rs.getInt("booking_id"));
            fb.setFlightId(rs.getInt("flight_id"));
            return fb;
        } catch (Exception e)
        {
            return null;
        }
    }

    public Flight getFlight(FlightBooking fb)
    {
        Flight f = new Flight();
        String sql = "select * from flight where id=" + fb.getFlightId();
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                FlightDao fd = new FlightDao();
                f = fd.parseFlightSQL(rs);
                return f;
            }
        } catch (Exception e)
        {
            System.out.println("Record not found");
        }

        return null;
    }

    @Override
    public String buildString(FlightBooking f)
    {
        return f.getFlightId() +
            ", " + f.getBookingId();
    }
    @Override
    public List<FlightBooking> getAll() {
        return null;
    }

    @Override
    public String buildStringUpdate(FlightBooking f) {
        return "flight_id = " + f.getFlightId() +
                ", booking_id = " + f.getBookingId();
    }

    @Override
    public FlightBooking save(FlightBooking flightBooking, Connection conn) {
        try
        {
            String sql = "insert into " + this.dbName + " values (" +
                    flightBooking.getFlightId() + "," +
                    flightBooking.getBookingId() + ")";
            ResultSet rs = AbstractDao.insertInto(sql, conn);

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return flightBooking;
    }

    @Override
    public FlightBooking update(FlightBooking flightBooking, Connection conn) {
        return null;
    }

    @Override
    public void delete(FlightBooking flightBooking, Connection conn) {

    }
}
