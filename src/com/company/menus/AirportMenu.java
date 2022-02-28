package com.company.menus;

import com.company.Menu;
import com.company.Option;
import com.company.OptionContainer;
import com.company.beans.Airport;
import com.company.beans.User;
import com.company.dao.AbstractDao;
import com.company.dao.AirportDao;
import java.sql.Connection;
import java.util.*;

public class AirportMenu {

    public static Option createAirport(User u)
    {
        return () ->
        {
            Scanner reader = new Scanner(System.in);
            String iata = properIata("Enter the IATA of the Airport you want to create");
            String city = reader.nextLine();
            Airport a = new Airport(iata, city);
            AirportDao ad = new AirportDao();
            try
            {
                Connection conn = AbstractDao.getConn();
                Objects.requireNonNull(conn).setAutoCommit(false);
                ad.save(a, conn);
                conn.commit();
                conn.setAutoCommit(true);
                return manageAirport(u,a);
            } catch (Exception e)
            {
                System.out.println("Something went wrong, returning to admin menu");
            }
            return AdminMenu.adminMain(u);
        };

    }
    public static Option chooseAirport(User u)
    {
        return () ->
        {
            String iata = properIata("Enter the IATA Id of the Airport you want to edit");
            AirportDao ad = new AirportDao();
            Airport a = ad.getById(iata);
            if (a != null)
            {
                return manageAirport(u,a);
            }
            return AdminMenu.adminMain(u);
        };
    }

    public static String properIata(String message)
    {
        System.out.println(message);
        Scanner reader = new Scanner(System.in);
        try
        {
            String st = reader.nextLine();
            if (st.length() == 3)
            {
                return st.toUpperCase(Locale.ROOT);
            } else {
                throw new InputMismatchException();
            }
        }
        catch (Exception e)
        {
            System.out.println("Invalid entry");
            return properIata(message);
        }
    }

    public static Option changeIata(User u, Airport a)
    {
        return () ->
        {
            a.setIataId(properIata("Please enter new IATA ID"));
            return manageAirport(u,a);
        };
    }

    public static Option changeCity(User u, Airport a)
    {
        return () ->
        {
            System.out.println("Please enter new City name");
            Scanner reader = new Scanner(System.in);
            a.setCity(reader.nextLine());
            return manageAirport(u,a);
        };
    }

    public static Option deleteAirport(User u, Airport a)
    {
        return () ->
        {
            System.out.println("Are you sure you want to delete this flight?");
            if (FlightMenu.properYes())
            {
                System.out.println("Deleting entry");
                try
                {
                    Connection conn = AbstractDao.getConn();
                    AirportDao fd = new AirportDao();
                    fd.delete(a,conn);
                    return AdminMenu.adminMain(u);

                } catch (Exception e)
                {
                    System.out.println("Something went wrong. Please try again.");

                }

            }
            return manageAirport(u,a);
        };
    }

    public static Option saveAirport(User u, Airport a)
    {
        return () -> {
            AirportDao fd = new AirportDao();
            Connection conn = AbstractDao.getConn();
            try
            {
                Objects.requireNonNull(conn).setAutoCommit(false);
                fd.update(a, conn);
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e)
            {
                System.out.println(e);
            }
            return manageAirport(u,a);
        };
    }
    public static Option returnTo(User u)
    {
        return () ->
                AdminMenu.adminMain(u);
    }

    public static Menu manageAirport(User u, Airport a)
    {

        ArrayList<OptionContainer> options = new ArrayList<>();
        options.add(new OptionContainer(
                changeIata(u,a), "IATA: " + a.getIataId()
                ));
        options.add(new OptionContainer(
                changeCity(u,a), "City: " + a.getCity()
        ));
        options.add(new OptionContainer(
                saveAirport(u,a), "Save"
        ));
        options.add(new OptionContainer(
                deleteAirport(u,a), "Delete"
        ));
        options.add(new OptionContainer(
                returnTo(u), "Go to previous"
        ));
        return new Menu("Welcome to the Aiport Management menu. Select an option", options);
    }
}
