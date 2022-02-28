package com.company.dao;

import com.company.beans.AirplaneType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AirplaneTypeDao implements AbstractDao<AirplaneType>{

    public String dbName;
    public AirplaneTypeDao()
    {
        this.dbName = "airplane_type";
    }
    @Override
    public Optional get(long id) {
        return Optional.empty();
    }

    public AirplaneType getById(int id)
    {
        AirplaneType at = new AirplaneType();
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery("select * from airplane_type where id=" + id);
            while (rs.next())
            {
                at = parseAirplaneSQL(rs);
            }
            return at;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public String buildString(AirplaneType a)
    {
        return a.getId() +
                ", " + a.getMaxCapacity();
    }

    public AirplaneType parseAirplaneSQL(ResultSet rs)
    {
        AirplaneType at = new AirplaneType();
        try {
            at.setId(rs.getInt("id"));
            at.setMaxCapacity(rs.getInt("max_capacity"));
            return at;
        } catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public String buildStringUpdate(AirplaneType a) {
        return "id = " + a.getId() +
                ", max_capacity = " + a.getMaxCapacity();
    }

    @Override
    public AirplaneType save(AirplaneType a, Connection conn) {
        try
        {
            String sql = "insert into " + this.dbName + " values (" + buildString(a) + ")";
            ResultSet rs = AbstractDao.insertInto(sql,conn);
            while (Objects.requireNonNull(rs).next())
            {
                a.setId(rs.getInt(1));
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return a;
    }

    @Override
    public AirplaneType update(AirplaneType a, Connection conn) {
       AirplaneTypeDao ud = new AirplaneTypeDao();
        String sql = "update " + ud.dbName + " set " + ud.buildString(a) + " where id = " + a.getId();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return a;
    }

    @Override
    public void delete(AirplaneType a, Connection conn) {
        AirplaneTypeDao ud = new AirplaneTypeDao();
        String sql = "delete from " + ud.dbName + " where id=" + a.getId();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
