package com.example.expendituremanagementapp.model;

import android.database.sqlite.SQLiteDatabase;

import com.example.expendituremanagementapp.database.DatabaseHelper;

public class User {
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db = dbHelper.getWritableDatabase();

    private static int id = -1;
    private static String username="", password="";

    public static int getId() {
        return User.id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public static String getUsername() {
        return User.username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

//    public static boolean checkLogin(String username, String password) {
//        return (User.username.equals(username) && User.password.equals(password));
//    }
}
