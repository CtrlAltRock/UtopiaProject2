package com.company.menus;

import com.company.Menu;
import com.company.Option;
import com.company.OptionContainer;
import com.company.beans.Flight;
import com.company.beans.User;
import com.company.dao.FlightDao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu {

    private static Option manageFlights(User u)
    {
        return () -> {
            Scanner reader = new Scanner(System.in);
            System.out.println("Please enter Flight Id of the flight you want to manage");
            try
            {
                int Id = reader.nextInt();
                FlightDao fd = new FlightDao();
                Flight f = fd.getById(Id);
                return FlightMenu.flightDetails(u,f);

            } catch (InputMismatchException e)
            {
                System.out.println("Invalid entry, try again.");
                return adminMain(u);
            }

        };
    }

    public static Menu adminMain(User u)
    {
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();
        options.add(new OptionContainer(manageFlights(u), "Manage Flights"));
        options.add(new OptionContainer(FlightMenu.createFlight(u, new Flight()),"Create new flight"));
        options.add(new OptionContainer(TravelerMenu.selectPassenger(u), "Manage Tickets"));
        options.add(new OptionContainer(AirportMenu.chooseAirport(u), "Manage Airports"));
        options.add(new OptionContainer(AirportMenu.createAirport(u), "Create Airport"));
        options.add(new OptionContainer(BookingMenus.chooseBooking(u), "Cancel/Revive booking"));
        options.add(new OptionContainer(UserMenu.selectUser(u), "Manage Users"));
        options.add(new OptionContainer(LoginMenu.logout(),"Logout"));

        return new Menu("Admin Menu", options);
    }
}
