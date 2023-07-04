package com.example.expendituremanagementapp.model;

public class RevenueType {
    private int id = -1, userId = -1;
    private String name;

    public RevenueType() {}

    public RevenueType(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }
    public RevenueType(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
