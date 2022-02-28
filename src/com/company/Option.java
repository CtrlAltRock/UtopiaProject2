package com.company;

public interface Option {
    //always return menu, but actions can happen in between
    Menu action();
}
