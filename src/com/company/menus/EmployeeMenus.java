package com.company.menus;

import com.company.Menu;
import com.company.Option;
import com.company.OptionContainer;
import com.company.beans.User;


import java.util.ArrayList;

public class EmployeeMenus {

    public static Option manageFlights(User u) {
        return () -> {
            return employee2(u);
        };
    }

    public static Menu employee1(User u)
    {
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();
        options.add(new OptionContainer(manageFlights(u), "Enter Flights You Manage"));
        options.add(new OptionContainer(LoginMenu.logout(),"Log out"));
        return new Menu("Employee Menu", options);
    }

    public static Menu employee2(User u)
    {
        ArrayList<OptionContainer> options = new ArrayList<OptionContainer>();

        return new Menu("Flights", options);

    }


}
