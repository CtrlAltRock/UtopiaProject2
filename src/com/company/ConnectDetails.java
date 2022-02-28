package com.company;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDetails {
    public static String url = "jdbc:mysql://localhost:3306/utopia";
    public static String name = "root";
    public static String password = "8BitOnion!";

    private Connection conn;

    public Connection getConn()
    {
        if (conn == null)
        {
            try
            {
               conn = DriverManager.getConnection(ConnectDetails.url, ConnectDetails.name, ConnectDetails.password);
            } catch (Exception e)
            {
                System.out.println(e);
                return null;
            }
        }
        return conn;
    }

}
