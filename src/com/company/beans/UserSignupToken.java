package com.company.beans;

import java.time.LocalDateTime;

public class UserSignupToken {
    private String confirmationToken;
    private LocalDateTime createdDate;
    private int id;
    private int userId;

    public UserSignupToken(String confirmationToken, LocalDateTime createdDate, int id, int userId) {
        this.confirmationToken = confirmationToken;
        this.createdDate = createdDate;
        this.id = id;
        this.userId = userId;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
