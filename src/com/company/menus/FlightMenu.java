package com.company.menus;

import com.company.Menu;
import com.company.Option;
import com.company.OptionContainer;
import com.company.ValidDateTime;
import com.company.beans.*;
import com.company.dao.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.*;

public class FlightMenu {

    public static String properDate(Scanner reader)
    {
        System.out.println("Enter Departure Time and Date (Format yyyy-mm-dd)");
        String date = reader.nextLine();
        try
        {
            if (ValidDateTime.isValidDate(date))
                return date;
            else
                throw new Exception();
        } catch (Exception e)
        {
            System.out.println("Incorrect format, please try again");
            return properDate(reader);
        }
    }

    public static int properPrice(Scanner reader)
    {
        try
        {
            System.out.println("Enter price of each seat");
            return reader.nextInt();
        } catch (InputMismatchException e)
        {
            System.out.println("Invalid Entry, please try again");
            return properPrice(reader);
        }
    }

    public static Airplane properAirplane(Scanner reader)
    {
        System.out.println("Enter Airplane Id");
        try
        {
            int id = reader.nextInt();
            AirplaneDao ad = new AirplaneDao();
            return ad.getById(id);
        } catch (Exception e)
        {
            System.out.println("Invalid Entry, please try again");
            return properAirplane(reader);
        }
    }

    public static Route properRoute(Scanner reader)
    {
        Route r = new Route();
        System.out.println("Enter Origin IATA ID");
        Airport origin = validIata(reader);
        System.out.println("Enter Destination IATA ID");
        Airport destination = validIata(reader);
        try
        {
            RouteDao rd = new RouteDao();
            r = rd.createOrUpdate(origin, destination);
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return r;
    }

    private static Airport validIata(Scanner reader)
    {
        String rt = reader.nextLine();
        AirportDao ad = new AirportDao();
        Airport a = ad.getById(rt);
        if (a != null)
        {
            return a;
        }
        System.out.println("Please enter a valid airport IATA");
        return validIata(reader);
    }

    public static Option updateDepartureTime(User u, Flight f)
    {
        return () ->
        {
            Scanner reader = new Scanner(System.in);
            f.setDepartureTime(properDate(reader));
            return flightDetails(u,f);
        };
    }

    public static Option updateSeatPrice(User u, Flight f)
    {
        return () ->
        {
            Scanner reader = new Scanner(System.in);
            f.setSeatPrice(properPrice(reader));
            return flightDetails(u,f);
        };
    }

    public static Option updateAirplane(User u, Flight f)
    {
        return () ->
        {
            Scanner reader = new Scanner(System.in);
            f.setAirplaneId(properAirplane(reader).getId());
            return flightDetails(u,f);
        };
    }

    public static Option updateRouteId(User u, Flight f)
    {
        return () -> {
            Scanner reader = new Scanner(System.in);
            f.setRouteId(properRoute(reader).getId());
            return flightDetails(u,f);
        };
    }

    public static Option saveFlight(User u, Flight f)
    {
        return () -> {
            FlightDao fd = new FlightDao();
            Connection conn = AbstractDao.getConn();
            try
            {
                Objects.requireNonNull(conn).setAutoCommit(false);
                fd.update(f, conn);
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e)
            {
                System.out.println(e);
            }
            return flightDetails(u,f);
        };
    }

    public static Option returnTo(User u)
    {
        return () ->
        {
            return AdminMenu.adminMain(u);
        };
    }

    public static Menu flightDetails(User u, Flight f)
    {
        System.out.println(f.flightRoute());
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();
        options.add(new OptionContainer(
                updateAirplane(u,f), "Update Airplane Type: " + f.getAirplaneId()
        ));
        options.add(new OptionContainer(
                updateDepartureTime(u,f), "Update Departure Time: " + f.getDepartureTime()
        ));
        options.add(new OptionContainer(
                updateSeatPrice(u,f), "Update Seat Price: " + f.getSeatPrice()
        ));
        options.add(new OptionContainer(
                updateRouteId(u,f), "Update Route Id: " + f.getRouteId()
        ));
        options.add(new OptionContainer(
                saveFlight(u,f),"Save Flight"
        ));
        options.add(new OptionContainer(
                deleteFlight(u,f),"Delete Flight"
        ));
        options.add(new OptionContainer(
                returnTo(u), "Return to previous menu"
        ));
        return new Menu("Flight: " + f.getId(), options);

    }

    public static boolean properYes()
    {
        Scanner reader = new Scanner(System.in);
        System.out.println("y/n");
        String entry = reader.nextLine().toLowerCase(Locale.ROOT);
        switch (entry)
        {
            case "y":
                return true;
            case "n":
                return false;
            default:
                System.out.println("Incorrect entry format, please try again.");
                return properYes();
        }
    }
    public static Option deleteFlight(User u, Flight f)
    {
        return () -> {
            System.out.println("Are you sure you want to delete this flight?");
            if (properYes())
            {
                System.out.println("Deleting entry");
                try
                {
                    Connection conn = AbstractDao.getConn();
                    FlightDao fd = new FlightDao();
                    fd.delete(f,conn);
                    return AdminMenu.adminMain(u);

                } catch (Exception e)
                {
                    System.out.println("Something went wrong. Please try again.");

                }

            }
            return flightDetails(u,f);
        };

    }

    public static int tableFlightSize()
    {
        try
        {
            Connection conn = AbstractDao.getConn();
            Statement stmt = Objects.requireNonNull(conn).createStatement();
            ResultSet rs = stmt.executeQuery("select max(id) from flight");
            while (rs.next())
            {
                return rs.getInt(1) + 1;
            }
            return 0;
        } catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
    }


    public static Option createFlight(User u, Flight f)
    {
        return () -> {
            Scanner reader = new Scanner(System.in);
            Route r = properRoute(reader);
            f.setRouteId(r.getId());
            f.setId(tableFlightSize());
            System.out.println("Enter id of airplane");
            f.setAirplaneId(properAirplane(reader).getId());
            f.setDepartureTime(properDate(reader));
            f.setReservedSeats(0);
            f.setSeatPrice(properPrice(reader));
            try
            {
                FlightDao fd = new FlightDao();
                RouteDao rd = new RouteDao();
                Connection conn = AbstractDao.getConn();
                Objects.requireNonNull(conn).setAutoCommit(false);
                fd.save(f, conn);
                System.out.println("New flight created!");
                System.out.println(f.flightRoute());
                conn.commit();
                conn.setAutoCommit(true);

            } catch (Exception e)
            {
                System.out.println(e);
                System.out.println("Something went wrong");

            }
            return FlightMenu.flightDetails(u,f);
        };
    }
}
