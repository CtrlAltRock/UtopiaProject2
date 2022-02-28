package com.company.dao;

import com.company.beans.Booking;
import com.company.beans.Flight;
import com.company.beans.FlightBooking;
import com.company.beans.Passenger;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BookingDao implements AbstractDao<Booking>{
    @Override
    public Optional<Booking> get(long id) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public String buildStringUpdate(Booking b) {
        return "id = " + b.getId() +
                ", is_active = " + b.getIsActive() +
                ", confirmation_code = '" + b.getConfirmationCode() + "'";
    }

    public String dbName;

    public List<Passenger> getPassengers(Booking b)
    {
        List<Passenger> lPass = new ArrayList<>();
        try
        {
            Connection conn = AbstractDao.getConn();
            PassengerDao pd = new PassengerDao();
            String sql = "select * from " + pd.dbName + " where booking_id = " + b.getId();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Passenger p = pd.parsePassengerSQL(rs);
                if (p != null)
                {
                    lPass.add(p);
                }
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return lPass;
    }

    public BookingDao()
    {
        this.dbName = "booking";
    }

    public Booking getByConfirm(String confirm)
    {
        Booking b = null;
        Connection conn = AbstractDao.getConn();
        try
        {
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + this.dbName + " where confirmation_code = " + confirm);
            while (rs.next())
            {
                b = parseBookingSQL(rs);
            }
            return b;
        } catch (Exception e)
        {
            return null;
        }
    }

    public String buildString(Booking b)
    {
        return b.getId() +
                ", " + b.getIsActive() +
                ", '" + b.getConfirmationCode() + "'";
    }

    @Override
    public Booking save(Booking b, Connection conn) {
        try
        {
            String sql = "insert into " + this.dbName + " values (" +
                    b.getId() + "," +
                    b.getIsActive() + ",'" +
                    b.getConfirmationCode() +"')";
            ResultSet rs = AbstractDao.insertInto(sql, conn);
            while (Objects.requireNonNull(rs).next())
            {
                b.setId(rs.getInt(1));
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return b;
    }

    public Flight getFlight(Booking b) {
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery("select * from flight_bookings where booking_id = " + b.getId());
            while (rs.next())
            {
                FlightBookingDao fbd = new FlightBookingDao();
                FlightBooking fb = fbd.parseFBSQL(rs);
                return fbd.getFlight(fb);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public Booking parseBookingSQL(ResultSet rs)
    {
        Booking b = new Booking();
        try
        {
            b.setId(rs.getInt("id"));
            b.setIsActive(rs.getInt("is_active"));
            b.setConfirmationCode(rs.getString("confirmation_code"));
            return b;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public Booking update(Booking b, Connection conn) {
        BookingDao ud = new BookingDao();
        String sql = "update " + ud.dbName + " set " + ud.buildStringUpdate(b) + " where id = " + b.getId();
        try {
            Statement stmt = conn.createStatement();
            int Return = stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return b;
    }

    @Override
    public void delete(Booking b, Connection conn) {
       BookingDao ud = new BookingDao();
        String sql = "delete from " + ud.dbName + " where id=" + b.getId();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
