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

public class LoginMenu {

    public static Option logout()
    {
        return () -> {
            return loginOptions();
        };
    }
    public static Option login()
    {
        return () ->
            {
            UserDao ud = new UserDao();
            Scanner reader = new Scanner(System.in);
            System.out.println("Please Enter Username");
            String username = reader.nextLine();
            System.out.println("Please Enter Password");
            String password = reader.nextLine();
            User u = ud.getByUsername(username);
                if (password.equals(u.getPassword()))
                {
                    return mainMenu.main(u);
                } else {
                    System.out.println("Username/Password does not match or User does not exist!");
                    return LoginMenu.loginOptions();
                }
            };
    }
    public static Option signUp()
    {
        return () ->
        {
            UserDao ud = new UserDao();
            Scanner reader = new Scanner(System.in);
            System.out.println("Let's sign you up!");
            System.out.println("Please enter your first name");
            String first = reader.nextLine();
            System.out.println("Please enter your last name");
            String last = reader.nextLine();
            System.out.println("Please enter your username");
            String username = reader.nextLine();
            System.out.println("Please enter your email");
            String email = reader.nextLine();
            System.out.println("Please enter your phone number");
            String phone = reader.nextLine();
            System.out.println("Please enter your password");
            String password = reader.nextLine();

            User u = new User(email,first,last,password,phone,username);
            Connection conn = AbstractDao.getConn();
            try
            {
                conn.setAutoCommit(false);
                u = ud.save(u, conn);
                conn.commit();
                conn.setAutoCommit(true);
                System.out.println("Welcome, " + u.getUsername() + "! Your membership ID is " + u.getId());
                System.out.println("Please write it down for your records!");
                return mainMenu.main(u);
            } catch (Exception e) {
                System.out.println(e);
            }

            return mainMenu.main(u);
        };
    }

    public static Menu loginOptions()
    {
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();
        options.add(new OptionContainer(login(), "Login"));
        options.add(new OptionContainer(signUp(), "Signup"));
        return new Menu("Welcome to the Utopia Airlines Management System! Please login or sign up!", options);
    }
}
