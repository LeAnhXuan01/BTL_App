package com.example.expendituremanagementapp.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.expendituremanagementapp.MainActivity;
import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private DatabaseHelper database;
    private int userId = 1;
    private TextView tvMoney;
    private TextView tvExpense;
    private TextView tvRevenue;
    private float money = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMoney = view.findViewById(R.id.tv_Money);
        tvExpense = view.findViewById(R.id.money_expense);
        tvRevenue = view.findViewById(R.id.money_revenue);
        loadData();
    }

    private void loadData() {
        userId = ((MainActivity) getActivity()).userID();
        database = new DatabaseHelper(requireView().getContext());
        money = getPriceRenevue() - getPriceExpense();
        tvMoney.setText(money + "");
        tvExpense.setText(getPriceExpense() + "");
        tvRevenue.setText(getPriceRenevue() + "");
    }
    private float getPriceRenevue(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(price) FROM revenues WHERE userId = '"+userId+"'", null);
        while (cursor.moveToFirst()){
            float price = cursor.getFloat(0);
            return price;
        }
        return 0;
    }
    private float getPriceExpense(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(price) FROM expenses WHERE userId = '"+userId+"'", null);
        while (cursor.moveToFirst()){
            float price = cursor.getFloat(0);
            return price;
        }
        return 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}