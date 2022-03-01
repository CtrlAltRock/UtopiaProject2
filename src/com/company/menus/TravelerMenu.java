package com.company.menus;

import com.company.Menu;
import com.company.Option;
import com.company.OptionContainer;
import com.company.ValidDateTime;
import com.company.beans.*;
import com.company.dao.*;

import java.sql.Connection;
import java.util.*;

public class TravelerMenu {

    //options
    public static Option bookFlight(User u) {
        return () ->
        {
            ArrayList<Flight> rFlights = new ArrayList<Flight>();
            FlightDao fd = new FlightDao();
            List<Flight> flights = fd.getAll();
            Iterator ii = flights.iterator();
            while (ii.hasNext()) {
                Flight f = (Flight) ii.next();
                if (f.remainingSeats() > 0) {
                    rFlights.add(f);
                }
            }
            return TravelerMenu.bookFlightMenu(u, rFlights);
        };
    }

    private static Option cancelFlight(User u) {
        return () -> {
            return LoginMenu.loginOptions();
        };
    }

    private static Option bookSeats(User u, Flight f) {
        return () -> {
            return bookSeatsMenu(u, f);
        };
    }

    public static Option goHome(User u) {
        return () ->
        {
            if (u.getRoleId() == 2)
                return trav1(u);
            else
                return AgentMenu.agentEntry(u);
        };
    }

    public static Menu travelerMenu(User u, int tries) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter your Membership Number!");
        try {
            int id = reader.nextInt();
            if (id == u.getId()) {
                return trav1(u);
            }
        } catch (InputMismatchException e) {
            if (tries == 0) {
                System.out.println("Number of tries exceeded, logging you out");
                return LoginMenu.loginOptions();
            }
            System.out.println("Please enter a valid number!");

        }
        return travelerMenu(u, --tries);
    }

    public static Option returnTo1(User u) {
        return () -> {
            if (u.getRoleId() == 2)
                return travelerMenu(u, 3);
            return AgentMenu.agentEntry(u);
        };
    }


    public static Menu bookFlightMenu(User u, ArrayList<Flight> flights) {
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();
        Iterator iter = flights.iterator();
        while (iter.hasNext()) {
            Flight f = (Flight) iter.next();
            options.add(new OptionContainer(TravelerMenu.bookSeats(u, f), f.flightRoute()));
        }
        options.add(new OptionContainer(returnTo1(u), "Return to previous"));
        return new Menu("Which flight would you like to book a seat on?", options);

    }

    public static int expectNumberInput(Scanner reader) {
        try {
            return reader.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Not a number! Try again!");
            return expectNumberInput(reader);
        }
    }

    public static int properId() {
        System.out.println("Enter Passenger ID");
        try {
            Scanner reader = new Scanner(System.in);
            return reader.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid format");
            return properId();
        }
    }

    public static Option selectPassenger(User u) {
        return () ->
        {
            int id = properId();
            PassengerDao pd = new PassengerDao();
            Passenger p = pd.getById(id);
            if (p.getId() != 0) {
                return managePassenger(u, p);
            } else {
                System.out.println("Nobody with that Id");
                return AdminMenu.adminMain(u);
            }

        };
    }

    public static Option travSelectPassenger(User u, Passenger p) {
        return () ->
        {
            return managePassenger(u, p);
        };
    }


    public static Menu AdminTraveler(User u) {
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();


        return new Menu("Manage Travelers", options);
    }

    public static Option bookFlight(User u, Flight f) {
        return () -> {
            //how many passengers?
            Scanner reader = new Scanner(System.in);
            ArrayList<Passenger> pList = new ArrayList<Passenger>();
            System.out.println("How many passengers will be on this flight?");
            //get passengers
            int nPass = expectNumberInput(reader);
            for (int i = 0; i < nPass; i++) {
                pList.add(Passenger.passengerPrompt());
            }
            //create booking
            Connection conn = AbstractDao.getConn();
            try {
                conn.setAutoCommit(false);
                Booking b = new Booking();
                BookingDao bd = new BookingDao();
                b = bd.save(b, conn);
                //creating booking_user
                switch (u.getRoleId()) {
                    case 2 -> {
                        BookingUser bu = new BookingUser(b.getId(), u.getId());
                        //save booking user
                        BookingUserDao bud = new BookingUserDao();
                        bu = bud.save(bu, conn);
                    }
                    case 3 -> {
                        BookingAgent ba = new BookingAgent(b.getId(), u.getId());
                        //save booking agent
                        BookingAgentDao bad = new BookingAgentDao();
                        bad.save(ba, conn);
                    }
                    default -> {
                    }
                }
                //create flight booking
                FlightBooking fb = new FlightBooking(f.getId(), b.getId());
                //save it all
                FlightBookingDao fbd = new FlightBookingDao();
                fbd.save(fb, conn);

                for (int i = 0; i < pList.size(); i++) {
                    PassengerDao pd = new PassengerDao();
                    Passenger p = pList.get(i);
                    p.setBookingId(b.getId());
                    pd.save(p, conn);
                }
                conn.commit();
                conn.setAutoCommit(true);
                System.out.println("Your booking is done! Please keep the following for your records!");
                System.out.println("Confirmation Code: " + b.getConfirmationCode());
                System.out.println("Price: " + f.getSeatPrice() * pList.size());
                System.out.println("Flight Number: " + f.getId());
            } catch (Exception e) {
                System.out.println(e);
            }
            //distribute proper

            //print results

            if (u.getRoleId() == 2)
                return trav1(u);
            return AgentMenu.agentEntry(u);
        };
    }

    public static Menu bookSeatsMenu(User u, Flight f) {
        System.out.println("Flight " + f.getId());
        System.out.println("Departs: " + f.getDepartureTime());
        System.out.println("Seats remaining: " + f.remainingSeats());
        System.out.println("Price: " + f.getSeatPrice());
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();
        options.add(new OptionContainer(bookFlight(u, f), "Book this flight"));
        options.add(new OptionContainer(returnTo1(u), "Return to booking menu"));
        return new Menu("Would you like to book this flight?", options);

    }

    public static Option cancelFlights(User u) {
        return () ->
        {
            BookingUserDao bud = new BookingUserDao();
            List<BookingUser> buList = bud.getByUser(u);
            List<Booking> bList = new ArrayList<>();
            Iterator buIter = buList.iterator();
            while (buIter.hasNext()) {
                BookingUser bu = (BookingUser) buIter.next();
                bList.add(bud.getBooking(bu));
            }
            return flightManageMenu(u, bList);
        };
    }

    public static Option cancelBooking(User u, Booking b) {
        return () -> {
            System.out.println("Are you sure you want to cancel this booking?");
            if (FlightMenu.properYes()) {
                System.out.println("Deleting entry");
                try {
                    Connection conn = AbstractDao.getConn();
                    BookingDao fd = new BookingDao();
                    b.setIsActive(0);
                    fd.update(b, conn);

                } catch (Exception e) {
                    System.out.println("Something went wrong. Please try again.");

                }

            }
            if (u.getRoleId() == 2)
                return trav1(u);
            return AgentMenu.agentEntry(u);
        };
    }

    public static Option goToManage(User u, Booking b) {
        return () ->
        {
            return travManagePassenger(u, b);
        };
    }

    public static Menu flightManageMenu(User u, List<Booking> bList) {
        ArrayList<OptionContainer> options = new ArrayList<>();
        Iterator bIter = bList.iterator();
        BookingDao bd = new BookingDao();
        while (bIter.hasNext()) {

            Booking b = (Booking) bIter.next();
            if (b == null)
                continue;
            Flight f = bd.getFlight(b);
            options.add(new OptionContainer(goToManage(u, b), f.flightRoute() + " confirmation code: " + b.getConfirmationCode()));
        }
        options.add(new OptionContainer(goHome(u), "Exit Menu"));
        return new Menu("Manage Flight Menu", options);
    }

    public static Menu trav1(User u) {
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();
        options.add(new OptionContainer(bookFlight(u), "Book a Ticket"));
        options.add(new OptionContainer(cancelFlights(u), "Manage an Upcoming Trip"));
        options.add(new OptionContainer(LoginMenu.logout(), "Log out"));
        return new Menu("What do you want to do today, " + u.getUsername(), options);
    }

    public static Menu travManagePassenger(User u, Booking b) {
        ArrayList<OptionContainer> options = new ArrayList<>();
        BookingDao bd = new BookingDao();
        List<Passenger> lPass = bd.getPassengers(b);
        Iterator iter = lPass.iterator();
        while (iter.hasNext()) {
            Passenger p = (Passenger) iter.next();
            options.add(new OptionContainer(
                    travSelectPassenger(u, p), "Manage Passenger: " + p.getFirstName() + " " + p.getLastName()
            ));
        }
        options.add(new OptionContainer(cancelBooking(u, b), "Cancel Booking "));
        options.add(new OptionContainer(goHome(u), "Exit Menu"));

        return new Menu("Manage Booking " + b.getConfirmationCode(), options);
    }

    public static Option UpdateGivenName(User u, Passenger p) {
        return () ->
        {
            System.out.println("Enter new first name");
            Scanner reader = new Scanner(System.in);
            p.setFirstName(reader.nextLine());
            return managePassenger(u, p);
        };

    }

    public static Option UpdateLastName(User u, Passenger p) {
        return () ->
        {
            System.out.println("Enter new last name");
            Scanner reader = new Scanner(System.in);
            p.setLastName(reader.nextLine());
            return managePassenger(u, p);
        };
    }

    public static String properDate(Scanner reader) {
        String date = reader.nextLine();
        if (ValidDateTime.isValidDate(date))
            return date;
        else
            return properDate(reader);
    }

    public static Option UpdateDob(User u, Passenger p) {
        return () ->
        {
            System.out.println("Enter new date of birth");
            Scanner reader = new Scanner(System.in);
            String date = properDate(reader);
            p.setDob(reader.nextLine());
            return managePassenger(u, p);
        };
    }

    public static Option UpdateGender(User u, Passenger p) {
        return () ->
        {
            System.out.println("Enter gender");
            Scanner reader = new Scanner(System.in);
            p.setGender(reader.nextLine());
            return managePassenger(u, p);
        };
    }

    public static Option UpdateAddress(User u, Passenger p) {
        return () ->
        {
            System.out.println("Enter new address");
            Scanner reader = new Scanner(System.in);
            p.setAddress(reader.nextLine());
            return managePassenger(u, p);
        };
    }

    public static Option SavePassenger(User u, Passenger p) {
        return () ->
        {
            PassengerDao pd = new PassengerDao();
            Connection conn = AbstractDao.getConn();
            try {
                conn.setAutoCommit(false);
                pd.update(p, conn);
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e) {
                System.out.println(e);
            }
            return managePassenger(u, p);
        };
    }

    public static Option DeletePassenger(User u, Passenger p) {
        return () ->
        {
            System.out.println("Are you sure you want to delete this Passenger?");
            if (FlightMenu.properYes()) {
                System.out.println("Deleting entry");
                try {
                    Connection conn = AbstractDao.getConn();
                    PassengerDao fd = new PassengerDao();
                    fd.delete(p, conn);
                    if (u.getRoleId() == 1)
                        return AdminMenu.adminMain(u);
                    else if (u.getRoleId() == 2)
                        return trav1(u);
                    return AgentMenu.agentEntry(u);
                } catch (Exception e) {
                    System.out.println("Something went wrong. Please try again.");

                }

            }
            return managePassenger(u, p);
        };
    }

    public static Option returnAdmin(User u) {
        return () -> {
            if (u.getRoleId() == 1) {
                return AdminMenu.adminMain(u);
            } else if (u.getRoleId() == 2) {
                return trav1(u);
            }
            return AgentMenu.agentEntry(u);

        };
    }


    public static Menu managePassenger(User u, Passenger p) {
        System.out.println("Passenger ID: " + p.getId());
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();
        options.add(new OptionContainer(UpdateGivenName(u, p)
                , "First Name: " + p.getFirstName()
        ));
        options.add(new OptionContainer(UpdateLastName(u, p)
                , "Last Name: " + p.getLastName()
        ));
        options.add(new OptionContainer(UpdateDob(u, p)
                , "Date of Birth: " + p.getDob()
        ));
        options.add(new OptionContainer(UpdateGender(u, p)
                , "Gender: " + p.getGender()
        ));
        options.add(new OptionContainer(UpdateAddress(u, p)
                , "Address: " + p.getAddress()
        ));
        options.add(new OptionContainer(SavePassenger(u, p)
                , "Save Passenger"
        ));
        options.add(new OptionContainer(DeletePassenger(u, p)
                , "Delete Passenger"
        ));
        options.add(new OptionContainer(returnAdmin(u), "Return to menu"));
        return new Menu("Manage Passenger", options);

    }
}
