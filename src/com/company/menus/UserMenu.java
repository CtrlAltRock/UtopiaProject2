package com.company.menus;

import com.company.Menu;
import com.company.Option;
import com.company.OptionContainer;
import com.company.beans.User;
import com.company.dao.AbstractDao;
import com.company.dao.UserDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class UserMenu {

    public static Option selectUser(User u)
    {
        return () ->
        {
            int id = properInt("Please enter the User Id you wish to manage");
            UserDao ud = new UserDao();
            User mu = ud.getById(id);
            if (mu != null)
            {
                return manageUser(u,mu);
            } else
            {
                System.out.println("User does not exist");
                return AdminMenu.adminMain(u);
            }
        };
    }

    public static int properInt(String message)
    {
        Scanner reader = new Scanner(System.in);
        System.out.println(message);
        try
        {
            return reader.nextInt();
        } catch (Exception e)
        {
            System.out.println("Incorrect format, try again");
            return properInt(message);
        }
    }

    public static int properRole()
    {
        try
        {
            Scanner reader = new Scanner(System.in);
            int ri = reader.nextInt();
            if (ri > 0 & ri < 4)
                return ri;
            else
                return properRole();

        }  catch (Exception e)
        {
            System.out.println("Invalid format");
            return properRole();
        }
    }

    public static Option updateRole(User u, User mu)
    {
        return () ->
        {
            System.out.println("Enter new role:");
            System.out.println("1. Admin");
            System.out.println("2: Customer");
            System.out.println("3: Agent");
            mu.setRoleId(properRole());
            return manageUser(u, mu);
        };
    }

    public static Option updateFirstName(User u, User mu)
    {
        return () ->
        {
            System.out.println("Enter new first name");
            Scanner reader = new Scanner(System.in);
            mu.setGivenName(reader.nextLine());
            return manageUser(u, mu);
        };
    }

    public static Option updateLastName(User u, User mu)
    {
        return () ->
        {
            System.out.println("Enter new last name");
            Scanner reader = new Scanner(System.in);
            mu.setFamilyName(reader.nextLine());
            return manageUser(u, mu);
        };
    }

    public static Option updatePhone(User u, User mu)
    {
        return () ->
        {
            System.out.println("Enter new phone number");
            Scanner reader = new Scanner(System.in);
            mu.setPhone(reader.nextLine());
            return manageUser(u, mu);
        };
    }

    public static Option saveUser(User u, User mu)
    {
        return () -> {
            UserDao fd = new UserDao();
            Connection conn = AbstractDao.getConn();
            try
            {
                conn.setAutoCommit(false);
                fd.update(mu, conn);
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e)
            {
                System.out.println(e);
            }
            return manageUser(u,mu);
        };
    }

    public static Option deleteUser(User u, User mu)
    {
        return () -> {
            System.out.println("Are you sure you want to delete this user?");
            if (FlightMenu.properYes())
            {
                System.out.println("Deleting entry");
                try
                {
                    Connection conn = AbstractDao.getConn();
                    UserDao fd = new UserDao();
                    fd.delete(mu,conn);
                    return AdminMenu.adminMain(u);

                } catch (Exception e)
                {
                    System.out.println("Something went wrong. Please try again.");

                }

            }
            return manageUser(u,mu);
        };

    }

    public static Option goHome(User u)
    {
        return () -> {
            return AdminMenu.adminMain(u);
        };
    }
    public static Menu manageUser(User u, User mu)
    {
        String[] roles = new String[]{"Admin","User","Agent"};
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();
        options.add(new OptionContainer(
                updateRole(u,mu),"Role " + roles[mu.getRoleId()-1]
    ));
        options.add(new OptionContainer(
                updateFirstName(u,mu),"Given Name: " + mu.getGivenName()
        ));
        options.add(new OptionContainer(
                updateLastName(u,mu),"Last Name: " + mu.getFamilyName()
        ));
        options.add(new OptionContainer(
                updatePhone(u,mu),"Phone: " + mu.getPhone()
        ));
        options.add(new OptionContainer(
                saveUser(u,mu),"Save User"
        ));
        options.add(new OptionContainer(
               deleteUser(u,mu),"Delete User " + mu.getUsername()
        ));
        options.add(new OptionContainer(
                goHome(u), "Return to previous menu"
        ));
        return new Menu("Managing User: " + mu.getId(), options);

    }
}
