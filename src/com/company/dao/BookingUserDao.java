package com.company.dao;

import com.company.beans.Booking;
import com.company.beans.BookingUser;
import com.company.beans.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BookingUserDao implements AbstractDao<BookingUser> {

    @Override
    public Optional<BookingUser> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<BookingUser> getAll() {
        return null;
    }

    @Override
    public String buildStringUpdate(BookingUser b) {
        return  "booking_id = " + b.getBooking_id() +
                ", user_id = " + b.getUser_id();
    }

    public List<BookingUser> getByUser(User u)
    {
        ArrayList<BookingUser> buList = new ArrayList<>();
        String sql = "select * from booking_user where user_id = " + u.getId();
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                BookingUser bu = new BookingUser();
                bu.setBooking_id(rs.getInt("booking_id"));
                bu.setUser_id(rs.getInt("user_id"));
                buList.add(bu);
            }
            return buList;
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public Booking getBooking(BookingUser bu)
    {
        String sql = "select * from booking where (id = " + bu.getBooking_id() + " and is_active = 1)";
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

    public String dbName;

    public BookingUserDao()
    {
        this.dbName = "booking_user";
    }

    public String buildString(BookingUser b)
    {
        return  b.getBooking_id() +
                ", " + b.getUser_id();
    }

    @Override
    public BookingUser save(BookingUser bookingUser, Connection conn) {
        try
        {
            String sql = "insert into booking_user values (" +
                    bookingUser.getBooking_id() + "," +
                    bookingUser.getUser_id() + ")";
            AbstractDao.insertInto(sql, conn);
        } catch (Exception e)
        {

        }
        return bookingUser;
    }

    @Override
    public BookingUser update(BookingUser bookingUser, Connection conn) {
        return null;
    }

    @Override
    public void delete(BookingUser bookingUser, Connection conn) {

    }
}
