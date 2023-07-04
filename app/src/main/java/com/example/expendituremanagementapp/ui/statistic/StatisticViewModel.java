package com.example.expendituremanagementapp.ui.statistic;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.expendituremanagementapp.MainActivity;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.helper.AsyncTaskExecutorService;
import com.example.expendituremanagementapp.model.ExpenseTypeStatistic;
import com.example.expendituremanagementapp.model.RevenueTypeStatistic;

import java.util.ArrayList;
import java.util.List;

public class StatisticViewModel extends AndroidViewModel {
    private DatabaseHelper db;
    private MutableLiveData<Float> totalRevenue, totalExpense;
    private MutableLiveData<List<RevenueTypeStatistic>> lsr;
    private MutableLiveData<List<ExpenseTypeStatistic>> lst;

    private int userId = 1;

    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public StatisticViewModel(Application application) {
        super(application);

        db = new DatabaseHelper(application);
        totalRevenue = new MutableLiveData<>();
        totalExpense = new MutableLiveData<>();
        lsr = new MutableLiveData<>();
        lst = new MutableLiveData<>();
    }

    public LiveData<List<RevenueTypeStatistic>> getListRevenueTypeStatistic() {
        new ListRevenueTypeStatisticAsyncTask().execute();
        return lsr;
    }

    public LiveData<List<ExpenseTypeStatistic>> getListExpenseTypeStatistic() {
        new ListExpenseTypeStatisticAsyncTask().execute();
        return lst;
    }

    public LiveData<Float> getTotalRevenue() {
        new TotalRevenueAsyncTask().execute();
        return totalRevenue;
    }

    public LiveData<Float> getTotalExpense() {
        new TotalExpenseAsyncTask().execute();
        return totalExpense;
    }

    private class ListRevenueTypeStatisticAsyncTask extends AsyncTaskExecutorService<Void, Void, List<RevenueTypeStatistic>> {
        protected List<RevenueTypeStatistic> doInBackground(Void params) {
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT b.id, b.name, " +
                    "sum(a.price) AS total FROM revenues a " +
                    "INNER JOIN revenue_types b ON a.revenueTypeId = b.id WHERE a.userId = '"+userId+"' " +
                    "GROUP BY  b.id, b.name", null);
            List<RevenueTypeStatistic> ls = new ArrayList<>();
            while (cursor.moveToNext()) {
                ls.add(new RevenueTypeStatistic(cursor.getInt(0),
                        cursor.getString(1), cursor.getFloat(2)));
            }
            return ls;
        }

        protected void onPostExecute(List<RevenueTypeStatistic> ls) {
            lsr.setValue(ls);
        }
    }

    private class ListExpenseTypeStatisticAsyncTask extends AsyncTaskExecutorService<Void, Void, List<ExpenseTypeStatistic>> {
        protected List<ExpenseTypeStatistic> doInBackground(Void params) {
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT b.id, b.name, " +
                    "sum(a.price) AS total FROM expenses a " +
                    "INNER JOIN expense_types b ON a.expenseTypeId = b.id WHERE a.userId = '"+userId+"' " +
                    "GROUP BY  b.id, b.name", null);
            List<ExpenseTypeStatistic> ls = new ArrayList<>();
            while (cursor.moveToNext()) {
                ls.add(new ExpenseTypeStatistic(cursor.getInt(0),
                        cursor.getString(1), cursor.getFloat(2)));
            }
            return ls;
        }

        protected void onPostExecute(List<ExpenseTypeStatistic> ls) {
            lst.setValue(ls);
        }
    }

    private class TotalRevenueAsyncTask extends AsyncTaskExecutorService<Void, Void, Float> {
        protected Float doInBackground(Void params) {
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT SUM(price) FROM revenues WHERE userId = '"+userId+"'", null);
            float sum = 0;
            if (cursor.moveToFirst()) {
                sum = cursor.getFloat(0);
            }
            return sum;
        }

        protected void onPostExecute(Float sum) {
            totalRevenue.setValue(sum);
        }
    }

    private class TotalExpenseAsyncTask extends AsyncTaskExecutorService<Void, Void, Float> {
        protected Float doInBackground(Void params) {
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT SUM(price) FROM expenses WHERE userId = '"+userId+"'", null);
            float sum = 0;
            if (cursor.moveToFirst()) {
                sum = cursor.getFloat(0);
            }
            return sum;
        }

        protected void onPostExecute(Float sum) {
            totalExpense.setValue(sum);
        }
    }
}