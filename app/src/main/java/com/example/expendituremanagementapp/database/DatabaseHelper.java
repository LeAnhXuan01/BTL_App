package com.example.expendituremanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expendituremanagementapp.model.Expense;
import com.example.expendituremanagementapp.model.Revenue;
import com.example.expendituremanagementapp.model.RevenueType;

import java.time.LocalDate;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "budget_management.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the necessary tables
        String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT)";
        db.execSQL(createUsersTableQuery);

        String createExpenseTypesTableQuery = "CREATE TABLE IF NOT EXISTS expense_types (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "userId INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id))";
        db.execSQL(createExpenseTypesTableQuery);

        String createRevenueTypesTableQuery = "CREATE TABLE IF NOT EXISTS revenue_types (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "userId INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id))";
        db.execSQL(createRevenueTypesTableQuery);

        String createExpensesTableQuery = "CREATE TABLE IF NOT EXISTS expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "price REAL, " +
                "note TEXT, " +
                "date DATE DEFAULT CURRENT_DATE, " +
                "userId INTEGER NOT NULL, " +
                "expenseTypeId INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,"+
                "FOREIGN KEY (expenseTypeId) REFERENCES expenses(id) ON DELETE CASCADE)";
        db.execSQL(createExpensesTableQuery);

        String createRevenuesTableQuery = "CREATE TABLE IF NOT EXISTS revenues (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "price REAL, " +
                "note TEXT, " +
                "date DATE DEFAULT CURRENT_DATE, " +
                "userId INTEGER NOT NULL, " +
                "revenueTypeId INTEGER NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,"+
                "FOREIGN KEY (revenueTypeId) REFERENCES revenues(id) ON DELETE CASCADE)";
        db.execSQL(createRevenuesTableQuery);
    }

    public long insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long userId = db.insert("users", null, values);
        db.close();
        return userId;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id"};
        String selection = "username = ?" + " AND " + "password = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("users", null, selection, selectionArgs, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        String selection = "username = ?";
        String[] selectionArgs = {username};
        int rowsAffected = db.update("users ", values, selection, selectionArgs);
        db.close();
        return (rowsAffected > 0);
    }

    public int getUserId(String username){
        String get = "SELECT id FROM users WHERE username = '"+username+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(get, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            return id;
        }
        return 0;
    }
    public String getUsernameId(int id){
        String get = "SELECT username FROM users WHERE id = '"+id+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(get, null);
        while (cursor.moveToNext()){
            String username = cursor.getString(0);
            return username;
        }
        return "";
    }


    public Cursor select(String table, int userID){
        String select = "SELECT * FROM "+table+" WHERE userId = "+userID+"";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(select, null);
    }
    public void insert_Revenue_Type(String name, int userID){
        String insert = "INSERT INTO revenue_types VALUES (null, '"+name+"', "+userID+")";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(insert);
    }
    public void update_Revenue_Type(RevenueType revenueType){
        String update = "UPDATE revenue_types SET name='"+revenueType.getName()+"' WHERE id='"+revenueType.getId()+"'";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(update);
    }
    public void delete_Revenue(String table, int id){
        String delete = "DELETE FROM "+table+" WHERE id = "+id+"";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(delete);
    }
    public void insert_Revenue(Revenue revenue){
        String insert = "INSERT INTO revenues VALUES(null, '"+ revenue.getName()+"', "+ revenue.getPrice()+", '"+ revenue.getNote()+"', '"+ revenue.getDate()+"', "+ revenue.getUserId()+", "+ revenue.getRevenueTypeId()+")";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(insert);
    }

    public void delete_Expense(String table, int id){
        String delete = "DELETE FROM "+table+" WHERE id = "+id+"";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(delete);
    }
    public void insert_Expense(Expense expense){
        String insert = "INSERT INTO expenses VALUES(null, '"+expense.getName()+"', "+expense.getPrice()+", '"+expense.getNote()+"', '"+expense.getDate()+"', "+expense.getUserId()+", "+expense.getExpenseTypeId()+")";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(insert);
    }
    public void update_Revenue(Revenue revenue){
        String update = "UPDATE revenues SET name = '"+ revenue.getName()+"', price = "+ revenue.getPrice()+", note = '"+ revenue.getNote()+"', date = '"+LocalDate.parse(revenue.getDate())+"' ";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(update);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Perform any necessary upgrades or migrations
        if (oldVersion < 1) {
            // Upgrade database from old version to version 1
        }
        onCreate(db);
    }
}
