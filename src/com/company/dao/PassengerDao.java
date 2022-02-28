package com.company.dao;

import com.company.beans.Passenger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class PassengerDao implements AbstractDao<Passenger> {

    public String dbName;

    public PassengerDao()
    {
        dbName = "passenger";
    }

    public String buildString(Passenger p)
    {
        return p.getId() +
                ", " + p.getBookingId() +
                ", '" + p.getFirstName() +
                "', '" + p.getLastName() +
                "', '" + p.getDob() +
                "', '" + p.getGender() +
                "', '" + p.getAddress() + "'";
    }

    @Override
    public Optional<Passenger> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Passenger> getAll() {
        return null;
    }

    public Passenger parsePassengerSQL(ResultSet rs)
    {
        Passenger p = new Passenger();
        try {
            p.setId(rs.getInt("id"));
            p.setBookingId(rs.getInt("booking_id"));
            p.setFirstName(rs.getString("given_name"));
            p.setLastName(rs.getString("family_name"));
            p.setDob(rs.getString("dob"));
            p.setGender(rs.getString("gender"));
            p.setAddress(rs.getString("address"));
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return p;
    }
    public Passenger getById(int id)
    {
        String sql = "select * from " + this.dbName + " where id = " + id + " limit 1";
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                return parsePassengerSQL(rs);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return new Passenger();
    }

    @Override
    public String buildStringUpdate(Passenger p) {
        return "id = " + p.getId() +
                ", booking_id = " + p.getBookingId() +
                ", given_name = '" + p.getFirstName() +
                "', family_name = '" + p.getLastName() +
                "', dob = '" + p.getDob() +
                "', gender = '" + p.getGender() +
                "', address = '" + p.getAddress() + "'";
    }

    @Override
    public Passenger save(Passenger p, Connection conn) {
        try
        {
            String sql = "insert into passenger values (" +
                    p.getId() + "," +
                    p.getBookingId() + ",'" +
                    p.getFirstName() + "','" +
                    p.getLastName() + "','" +
                    p.getDob() + "','" +
                    p.getGender() + "','" +
                    p.getAddress() + "')";
            ResultSet rs = AbstractDao.insertInto(sql, conn);
            while (rs.next())
            {
                p.setId(rs.getInt(1));
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return p;
    }

    @Override
    public Passenger update(Passenger p, Connection conn) {
        PassengerDao ud = new PassengerDao();
        String sql = "update " + ud.dbName + " set " + ud.buildStringUpdate(p) + " where id = " + p.getId();
        try {
            Statement stmt = conn.createStatement();
            int Return = stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return p;
    }

    @Override
    public void delete(Passenger passenger, Connection conn) {
        PassengerDao ud = new PassengerDao();
        String sql = "delete from " + ud.dbName + " where id=" + passenger.getId();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
