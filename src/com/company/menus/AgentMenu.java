package com.company.menus;

import com.company.Menu;
import com.company.Option;
import com.company.OptionContainer;
import com.company.beans.Booking;
import com.company.beans.BookingAgent;
import com.company.beans.User;
import com.company.dao.BookingAgentDao;

import java.awt.print.Book;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AgentMenu {

    public static Option manageFlights(User u)
    {
        return () ->
        {
            BookingAgentDao bad = new BookingAgentDao();
            List<BookingAgent> baList = bad.getByAgent(u);
            List<Booking> bList = new ArrayList<>();
            Iterator iter = baList.iterator();
            while (iter.hasNext())
            {
                BookingAgent ba = (BookingAgent)iter.next();
                Booking b = bad.getBooking(ba);
                bList.add(b);
            }
            return TravelerMenu.flightManageMenu(u, bList);
        };
    }

    public static Menu agentEntry(User u)
    {
        ArrayList<OptionContainer> options = new ArrayList<>();
        options.add(
                new OptionContainer(
                       manageFlights(u) ,"Manage your Flights"
                )
        );
        options.add(
                new OptionContainer(
                        TravelerMenu.bookFlight(u), "Create a booking"
                )
        );
        options.add(
                new OptionContainer(
                        LoginMenu.logout(), "Log out"
                )
        );
        return new Menu("Welcome, " + u.getUsername() + "! What would you like to do today?",options);
    }
}
