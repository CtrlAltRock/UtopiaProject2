package com.company;

import java.util.Iterator;
import java.util.List;

public class Menu {
    private final String header;
    private final List<OptionContainer> options;
    public Menu(String header, List<OptionContainer> options)
    {
        this.header = header;
        this.options = options;
    }
    public void display()
    {
        DisplayMethods.clear();
        System.out.println(this.header);
        Iterator<OptionContainer> iter = this.options.iterator();
        int count = 1;
        while (iter.hasNext())
        {
            System.out.print((count++) + ": ");
            iter.next().print();
        }
    }
    public Menu act(int select)
    {
        return this.options.get(select).act();
    }
}
