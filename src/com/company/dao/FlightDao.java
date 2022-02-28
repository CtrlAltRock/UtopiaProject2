package com.company.dao;


import com.company.beans.Flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FlightDao implements AbstractDao<Flight>{

    public String dbName;
    public FlightDao()
    {
        this.dbName = "flight";
    }

    public Flight parseFlightSQL(ResultSet rs)
    {
        Flight f = new Flight();
        try {
            f.setId(rs.getInt("id"));
            f.setRouteId(rs.getInt("route_id"));
            f.setAirplaneId(rs.getInt("airplane_id"));
            f.setDepartureTime(rs.getTime("departure_time").toString());
            f.setReservedSeats(rs.getInt("reserved_seats"));
            f.setSeatPrice(rs.getInt("seat_price"));
            return f;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public String buildString(Flight f)
    {
        return f.getId() +
                ", " + f.getRouteId() +
                ", " + f.getAirplaneId() +
                ", '" + f.getDepartureTime() +
                "', " + f.getReservedSeats() +
                ", " + f.getSeatPrice();
    }
    @Override
    public Optional get(long id) {
        return Optional.empty();
    }

    public Flight getById(int id)
    {
        Flight f = new Flight();
        Connection conn = AbstractDao.getConn();
        try
        {
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + this.dbName + " where id=" + id + " limit 1");
            while (rs.next())
            {
                f = parseFlightSQL(rs);
            }
            return f;
        } catch (Exception e)
        {
            System.out.println(e);
            return null;
        }

    }

    @Override
    public List getAll() {
        try {
            ArrayList<Flight> results = new ArrayList<Flight>();
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery("select * from flight limit 10");
            while (rs.next())
            {
                results.add(new Flight(
                    rs.getInt("id"),
                        rs.getInt("route_id"),
                        rs.getInt("airplane_id"),
                        rs.getTime("departure_time").toString(),
                        rs.getInt("reserved_seats"),
                        rs.getInt("seat_price")));
            }
            return results;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public String buildStringUpdate(Flight f) {
        return "id = " + f.getId() +
                ", route_id = " + f.getRouteId() +
                ", airplane_id = " + f.getAirplaneId() +
                ", departure_time = '" + f.getDepartureTime() +
                "', reserved_seats = " + f.getReservedSeats() +
                ", seat_price = " + f.getSeatPrice();
    }

    @Override
    public Flight save(Flight f, Connection conn) {
        try
        {
            String sql = "insert into " + this.dbName + " values (" + buildString(f) + ")";
            System.out.println(sql);
            ResultSet rs = AbstractDao.insertInto(sql, conn);
            while (Objects.requireNonNull(rs).next())
            {
                f.setId(rs.getInt(1));
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return f;
    }

    @Override
    public Flight update(Flight f, Connection conn) {
        FlightDao ud = new FlightDao();
        String sql = "update " + ud.dbName + " set " + ud.buildStringUpdate(f) + " where id = " + f.getId();
        System.out.println(sql);
        try {
            Statement stmt = conn.createStatement();
            int Return = stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return f;
    }

    @Override
    public void delete(Flight f, Connection conn) {
        FlightDao ud = new FlightDao();
        String sql = "delete from " + ud.dbName + " where id=" + f.getId();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
