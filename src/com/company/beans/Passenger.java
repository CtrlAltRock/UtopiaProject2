package com.company.beans;

import java.text.SimpleDateFormat;

import java.util.Scanner;

public class Passenger {
    private int id;
    private int bookingId;
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private String address;

    public Passenger(String firstName, String lastName,String dob, String gender, String address) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
    }

    public Passenger()
    {

    }

    public static Passenger passengerPrompt()
    {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter first name of passenger.");
        String first = reader.nextLine();
        System.out.println("Last name");
        String last = reader.nextLine();
        System.out.println("Birth date (format YYYY-MM-DD)");
        String date = reader.nextLine();
        System.out.println("Address");
        String address = reader.nextLine();
        System.out.println("Gender");
        String gender = reader.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            return new Passenger(first,last,date,gender,address);
        } catch (Exception e)
        {
            System.out.println("Incorrect Entry! Please try again!");
            return passengerPrompt();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
