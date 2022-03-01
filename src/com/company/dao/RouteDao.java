package com.company.dao;


import com.company.beans.Airport;
import com.company.beans.Route;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class RouteDao implements AbstractDao<Route> {

    public String dbName;

    public RouteDao() {
        this.dbName = "route";
    }

    @Override
    public Optional get(long id) {
        return Optional.empty();
    }

    public Route getById(int id) {
        Route r = new Route();
        Connection conn = AbstractDao.getConn();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from route where id=" + id);
            while (rs.next()) {
                r = parseRouteSQL(rs);
            }
            return r;
        } catch (Exception e) {
            return null;
        }
    }

    public String buildString(Route r)
    {
        return r.getId() +
                ", '" + r.getOriginId() +
                "', '" + r.getDestinationId() + "'";
    }

    public Route parseRouteSQL(ResultSet rs)
    {
        Route r = new Route();
        try {
            r.setId(rs.getInt("id"));
            r.setOriginId(rs.getString("origin_id"));
            r.setDestinationId(rs.getString("destination_id"));
            return r;
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
    public String buildStringUpdate(Route r) {
        return "id = " + r.getId() +
                ", origin_id = '" + r.getOriginId() +
                "', destination_id = '" + r.getDestinationId() + "'";
    }

    public Route createOrUpdate(Airport origin, Airport destination)
    {
        Route r = getByOD(origin,destination);
        if (r.getDestinationId() == null)
        {
            r.setOriginId(origin.getIataId());
            r.setDestinationId(destination.getIataId());
            r.setId(0);
            r = save(r, AbstractDao.getConn());
        }
        return r;
    }

    public Route getByOD(Airport origin, Airport destination)
    {
        Route r = new Route();
        String sql = "select * from " + this.dbName + " where origin_id = '" + origin.getIataId() + "' and destination_id = '" + destination.getIataId() + "'";
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                r = parseRouteSQL(rs);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return r;
    }

    @Override
    public Route save(Route r, Connection conn) {
        try
        {
            String sql = "insert into " + this.dbName + " values (" + buildString(r) + ")";
            System.out.println(sql);
            ResultSet rs = AbstractDao.insertInto(sql, conn);
            while (rs.next())
            {
               r.setId(rs.getInt(1));
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return r;
    }

    @Override
    public Route update(Route r, Connection conn) {
        RouteDao ud = new RouteDao();
        String sql = "update " + ud.dbName + " set " + ud.buildStringUpdate(r) + " where id = " + r.getId();
        try {
            Statement stmt = conn.createStatement();
            int Return = stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return r;
    }

    @Override
    public void delete(Route r, Connection conn) {
        RouteDao ud = new RouteDao();
        String sql = "delete from " + ud.dbName + " where id=" + r.getId();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
