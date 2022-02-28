package com.company.beans;


import java.util.List;

public class User {
    private String email;
    private String familyName;
    private String givenName;
    private int Id;
    private String password;
    private String phone;
    private int roleId;
    private String username;

    public User() {
    }

    public User(String email, String familyName, String givenName, String password, String phone, String username) {

        this.Id = 0;
        this.email = email;
        this.familyName = familyName;
        this.givenName = givenName;
        this.password = password;
        this.phone = phone;
        this.username = username;
        this.roleId = 2;
    }

    public List<Flight> getFlights()
    {
        return null;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
