package com.company;

public class OptionContainer {
    private final Option action;
    private final String display;
    public OptionContainer(Option action, String display)
    {
        this.action = action;
        this.display = display;
    }

    public void print()
    {
        System.out.println(this.display);
    }

    public Menu act()
    {
        return this.action.action();
    }
}
