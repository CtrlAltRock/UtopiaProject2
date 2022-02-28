package com.company.beans;



public class Booking {
    private int id;
    private int isActive;
    private String confirmationCode;

    public String generateConfirmCode()
    {
        String from = "1234567890";
        String to = "";
        for (int i = 0; i < 6; i ++)
        {
            int at = (int)(Math.random() * from.length());
            to += from.charAt(at);
        }
        return to;
    }

    public Booking() {
        this.id = 0;
        this.isActive = 1;
        this.confirmationCode = generateConfirmCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
