package com.company.dao;

import com.company.beans.Airport;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AirportDao implements AbstractDao<Airport> {

    @Override
    public Optional get(long id) {
        return Optional.empty();
    }

    public String dbName;

    public AirportDao()
    {
        this.dbName = "airport";
    }

    public Airport getById(String id)
    {
        Airport a = new Airport();

        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery("select * from airport where iata_id='" + id + "'");
            while (rs.next())
            {
                a = parseAirportSQL(rs);
            }
            return a;
        } catch (Exception e)
        {
            return null;
        }
    }

    public String buildString(Airport a)
    {
        return a.getIataId() +
                ", " + a.getCity();
    }

    public Airport parseAirportSQL(ResultSet rs)
    {
        Airport a = new Airport();
        try
        {
            a.setIataId(rs.getString("iata_id"));
            a.setCity(rs.getString("city"));
            return a;

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
    public String buildStringUpdate(Airport a) {
        return "iata_id = '" + a.getIataId() +
                "', city = '" + a.getCity() + "'";
    }

    @Override
    public Airport save(Airport a, Connection conn) {
        try
        {
            String sql = "insert into " + this.dbName + " values (" + buildString(a) + ")";
            ResultSet rs = AbstractDao.insertInto(sql, conn);
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return a;
    }

    @Override
    public Airport update(Airport a, Connection conn) {
        return null;
    }

    @Override
    public void delete(Airport a, Connection conn) {

    }
}
