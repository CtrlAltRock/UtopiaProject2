package com.company;

import com.company.menus.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {



        DisplayMethods.clear();

        //menu loop
        MenuDisplays currentMenu = new MenuDisplays();
        //initialize with main menu
        currentMenu.setMenu(LoginMenu.loginOptions());
        Scanner reader = new Scanner(System.in);

        while (true)
        {
            //display the menu
            currentMenu.displayMenu();
            //ask for input
            int selection = reader.nextInt();

            //verify input
            //run action
            try
            {
                currentMenu.setMenu(currentMenu.act(selection - 1));
            } catch (Exception e)
            {
                currentMenu.setMenu(LoginMenu.loginOptions());
            }


        }
    }
}
