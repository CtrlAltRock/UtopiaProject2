package com.company.dao;

import com.company.ConnectDetails;


import java.sql.*;
import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> {
    Optional<T> get(long id);

    List<T> getAll();

    static Connection getConn()
    {
        try
        {
            return DriverManager.getConnection(ConnectDetails.url, ConnectDetails.name, ConnectDetails.password);
        } catch (Exception e)
        {
            System.out.println(e);
            return null;
        }

    }

    String buildStringUpdate(T t);

    String buildString(T t);

    T save(T t, Connection conn);

    T update(T t, Connection conn);

    void delete(T t, Connection conn);

    static ResultSet insertInto(String sql, Connection conn)
    {
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.execute();
            return stmt.getGeneratedKeys();
        } catch (Exception e)
        {
            System.out.println(e);
            return null;
        }

    }

}
