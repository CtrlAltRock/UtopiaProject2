package com.company.dao;

import com.company.beans.Airplane;
import com.company.beans.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AirplaneDao implements AbstractDao<Airplane>{

    public String dbName;
    public AirplaneDao()
    {
        this.dbName = "airplane";
    }
    @Override
    public Optional get(long id) {
        return Optional.empty();
    }

    public Airplane getById(int id)
    {
        Airplane a = new Airplane();
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery("select * from airplane where id='" + id + "'");
            while (rs.next())
            {
                a = parseAirplaneSQL(rs);

            }
            return a;
        } catch (Exception e)
        {
            return null;
        }
    }

    public String buildString(Airplane a)
    {
        return a.getId() +
                ", " + a.getTypeId();
    }

    public Airplane parseAirplaneSQL(ResultSet rs) {
        Airplane a = new Airplane();
        try {
            a.setId(rs.getInt("id"));
            a.setTypeId(rs.getInt("type_id"));
            return a;
        } catch (Exception e) {
            return null;
        }
    }


        @Override
    public List getAll() {
        return null;
    }

    @Override
    public String buildStringUpdate(Airplane a) {
        return "id = " + a.getId() +
                ", type_id = " + a.getTypeId();
    }

    @Override
    public Airplane save(Airplane a, Connection conn)
    {
        try
        {
            String sql = "insert into " + this.dbName + " values (" + buildString(a) + ")";
            System.out.println(sql);
            ResultSet rs = AbstractDao.insertInto(sql, conn);
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
    public Airplane update(Airplane a, Connection conn) {
        AirplaneDao ud = new AirplaneDao();
        String sql = "update " + ud.dbName + " set " + ud.buildString(a) + " where id = " + a.getId();
        try {
            Statement stmt = conn.createStatement();
            int Return = stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return a;
    }

    @Override
    public void delete(Airplane a, Connection conn) {
        AirplaneDao ud = new AirplaneDao();
        String sql = "delete from " + ud.dbName + " where id=" + a.getId();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
