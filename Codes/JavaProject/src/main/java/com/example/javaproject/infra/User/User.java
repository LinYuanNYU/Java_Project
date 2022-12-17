package com.example.javaproject.infra.User;

public class User {
    private int userId;
    public User(int userId) {
        this.userId = userId;
    }
    public User() {}
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getId() {
        return userId;
    }
}
