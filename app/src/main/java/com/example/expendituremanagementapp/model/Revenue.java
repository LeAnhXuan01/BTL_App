package com.example.expendituremanagementapp.model;

public class Revenue {
    private int id = -1, revenueTypeId=-1, userId = -1;
    private String name, note;
    private float price;
    private String date;

    public Revenue() {}

    public Revenue(int id, String name, float price, String note, String date, int userId, int revenueTypeId) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.price = price;
        this.date = date;
        this.revenueTypeId = revenueTypeId;
        this.userId = userId;
    }
    public Revenue(String name, float price, String note, String date, int userId, int revenueTypeId) {
        this.name = name;
        this.note = note;
        this.price = price;
        this.date = date;
        this.revenueTypeId = revenueTypeId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRevenueTypeId() {
        return revenueTypeId;
    }

    public void setRevenueTypeId(int revenueTypeId) {
        this.revenueTypeId = revenueTypeId;
    }

    public String getDate() {
        return date;
    }
}
