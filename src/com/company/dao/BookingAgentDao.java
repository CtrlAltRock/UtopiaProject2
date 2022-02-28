package com.company.dao;

import com.company.beans.Booking;
import com.company.beans.BookingAgent;
import com.company.beans.BookingUser;
import com.company.beans.User;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BookingAgentDao implements AbstractDao<BookingAgent> {
    @Override
    public Optional<BookingAgent> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<BookingAgent> getAll() {
        return null;
    }

    @Override
    public String buildStringUpdate(BookingAgent b) {
        return  "id = " + b.getBookingId() +
                ", agent_id = " + b.getAgent_id();
    }

    public String dbName;

    public BookingAgentDao()
    {
        this.dbName = "booking_agent";
    }

    public List<BookingAgent> getByAgent(User u)
    {
        ArrayList<BookingAgent> buList = new ArrayList<>();
        String sql = "select * from " + this.dbName + " where agent_id = " + u.getId();
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                BookingAgent bu = new BookingAgent();
                bu.setBookingId(rs.getInt("booking_id"));
                bu.setAgent_id(rs.getInt("agent_id"));
                buList.add(bu);
            }
            return buList;
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public Booking getBooking(BookingAgent bu)
    {
        String sql = "select * from booking where (id = " + bu.getBookingId() + " and is_active = 1)";
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                BookingDao bd = new BookingDao();
                return bd.parseBookingSQL(rs);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public String buildString(BookingAgent b)
    {
        return  b.getBookingId() +
                ", " + b.getAgent_id();
    }
    @Override
    public BookingAgent save(BookingAgent bookingAgent, Connection conn) {
        try
        {
            String sql = "insert into " + this.dbName + " values (" +
                bookingAgent.getBookingId() + "," +
                bookingAgent.getAgent_id() + ")";
            AbstractDao.insertInto(sql, conn);
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return bookingAgent;
    }

    @Override
    public BookingAgent update(BookingAgent bookingAgent, Connection conn) {
       return null;
    }

    @Override
    public void delete(BookingAgent bookingAgent, Connection conn) {

    }
}
