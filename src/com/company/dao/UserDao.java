package com.company.dao;

import com.company.beans.User;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDao implements AbstractDao<User>{

    public String dbName;

    @Override
    public Optional<User> get(long id) {
        return Optional.empty();
    }

    public UserDao()
    {
        dbName = "user";
    }

    public User parseUserSQL(ResultSet rs)
    {
        User u = new User();
        try {
            u.setUsername(rs.getString("username"));
            u.setId(rs.getInt("id"));
            u.setRoleId(rs.getInt("role_id"));
            u.setGivenName(rs.getString("given_name"));
            u.setFamilyName(rs.getString("family_name"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setPhone(rs.getString("phone"));
            return u;
        } catch (Exception e)
        {
            return null;
        }
    }

    public String buildString(User u)
    {
        return u.getId() +
                ", " + u.getRoleId() +
                ", '" +u.getGivenName() +
                "', '" + u.getFamilyName() +
                "', '" + u.getUsername() +
                "', '" + u.getEmail() +
                "', '" + u.getPassword() +
                "', '" + u.getPhone() + "'";
    }

    public User getByUsername(String username)
    {
        User u = new User();
        Connection conn = AbstractDao.getConn();
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + this.dbName + " where username='" + username + "'");
            while (rs.next())
            {
                u = parseUserSQL(rs);

            }
            return u;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    public User getById(int id)
    {
        User u = new User();
        Connection conn = AbstractDao.getConn();
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + this.dbName + " where id=" + id);
            while (rs.next())
            {
                u = parseUserSQL(rs);

            }
            return u;
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public String buildStringUpdate(User u) {
        return "id = " + u.getId() +
                ", role_id = " + u.getRoleId() +
                ", given_name = '" +u.getGivenName() +
                "', family_name = '" + u.getFamilyName() +
                "', username = '" + u.getUsername() +
                "', email = '" + u.getEmail() +
                "', password = '" + u.getPassword() +
                "', phone = '" + u.getPhone() + "'";
    }


    @Override
    public User save(User user, Connection conn) {
            try
            {
                String sql = "insert into " + this.dbName + " values (" + buildStringUpdate(user) + ")";
                ResultSet rs = AbstractDao.insertInto(sql, conn);
                while (rs.next())
                {
                    user.setId(rs.getInt(1));
                }

            } catch (Exception e)
            {
                System.out.println(e);
            }
            return user;
    }

    @Override
    public User update(User user, Connection conn) {
        UserDao ud = new UserDao();
        String sql = "update " + ud.dbName + " set " + ud.buildStringUpdate(user) + " where id = " + user.getId();
        try {
            Statement stmt = conn.createStatement();
            int Return = stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    @Override
    public void delete(User user, Connection conn) {
        UserDao ud = new UserDao();
        String sql = "delete from " + ud.dbName + " where id=" + user.getId();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
