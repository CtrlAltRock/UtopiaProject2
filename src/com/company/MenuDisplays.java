package com.company;

public class MenuDisplays {
    private Menu currentMenu;

    public void displayMenu()
    {
        this.currentMenu.display();
    }

    public void setMenu(Menu m)
    {
        this.currentMenu = m;
    }

    public Menu act(int select)
    {
        return currentMenu.act(select);
    }

    public Menu getMenu() {
        return this.currentMenu;
    }
}
