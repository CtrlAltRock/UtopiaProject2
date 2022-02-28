package com.company.menus;

import com.company.*;
import com.company.beans.User;

public class mainMenu {
    //a collection of static menus
    private static Option goToEmployee(User u)
    {
        return () -> {
            return EmployeeMenus.employee1(u);
        };
    }

    public static Menu main(User u)
    {
        switch (u.getRoleId())
        {
            case 3:
                return AgentMenu.agentEntry(u);
            case 2:
                return TravelerMenu.travelerMenu(u,3);
            case 1:
                return AdminMenu.adminMain(u);
            default:
                return mainMenu.main(u);
        }

    }
}
