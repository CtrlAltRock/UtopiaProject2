package com.company.menus;

import com.company.Menu;
import com.company.Option;
import com.company.OptionContainer;
import com.company.beans.Booking;
import com.company.beans.User;
import com.company.dao.AbstractDao;
import com.company.dao.BookingDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class BookingMenus {

    public static String properConfirmation()
    {
        System.out.println("Enter Confirmation code");
        try
        {
            Scanner reader = new Scanner(System.in);
            String st = reader.nextLine();
            if (st.length() >= 6 & st.length() <= 9)
            {
                return st;
            }
            throw new Exception();
        } catch (Exception e)
        {
            System.out.println("Incorrect format");
        }
        return properConfirmation();
    }

    public static Option chooseBooking(User u)
    {
        return () ->
        {
            String confirm = properConfirmation();
            try
            {
                BookingDao bd = new BookingDao();
                Booking b = bd.getByConfirm(confirm);
                if (b == null)
                {
                    System.out.println("No booking with that code");
                    return AdminMenu.adminMain(u);
                }
                return bookingStatus(u,b);
            } catch (Exception e)
            {
                System.out.println(e);
            }
            return AdminMenu.adminMain(u);
        };
    }
    public static Option toggleBooking(User u, Booking b)
    {
        return () -> {
            b.setIsActive(b.getIsActive() == 1 ? 0 : 1);
            try
            {
                BookingDao bd = new BookingDao();
                Connection conn = AbstractDao.getConn();
                Booking bU = bd.update(b,conn);
                return bookingStatus(u, bU);
            } catch (Exception e)
            {
                System.out.println(e);
            }
            return bookingStatus(u,b);
        };
    }

    public static Option goHome(User u)
    {
        return () ->
        {
            return AdminMenu.adminMain(u);
        };
    }
    public static Menu bookingStatus(User u, Booking b)
    {
        ArrayList<OptionContainer> options = new ArrayList<>();
        options.add(new OptionContainer(
                toggleBooking(u,b),"Make " + (b.getIsActive() == 1 ? "inactive" : "active")
        ));
        options.add(new OptionContainer(
                goHome(u),"Return to previous"
        ));
        return new Menu("Booking: " + b.getConfirmationCode(),options);
    }


}
