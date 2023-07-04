package com.example.expendituremanagementapp.model;

import android.database.sqlite.SQLiteDatabase;

import com.example.expendituremanagementapp.database.DatabaseHelper;

public class ExpenseType {
//    private static DatabaseHelper dbHelper;
//    private static SQLiteDatabase db = dbHelper.getWritableDatabase();

    private int id = -1, userId = -1;
    private String name;

    public ExpenseType() {}

    public ExpenseType(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
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
