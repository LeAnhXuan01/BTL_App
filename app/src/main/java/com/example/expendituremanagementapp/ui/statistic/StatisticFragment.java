package com.example.expendituremanagementapp.ui.statistic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.expendituremanagementapp.MainActivity;
import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.adapter.ExpenseTypeStatisticRecyclerView;
import com.example.expendituremanagementapp.adapter.RevenueTypeStatisticRecyclerView;
import com.example.expendituremanagementapp.model.ExpenseTypeStatistic;
import com.example.expendituremanagementapp.model.RevenueTypeStatistic;

import java.util.List;

public class StatisticFragment extends Fragment {
    private RecyclerView rvRevenueTypeStatistic, rvExpenseTypeStatistic;
    private RevenueTypeStatisticRecyclerView revenueTypeStatisticAdapter;
    private ExpenseTypeStatisticRecyclerView expenseTypeStatisticAdapter;
    private StatisticViewModel statisticViewModel;
    private EditText etTotalRevenue, etTotalExpense;
    private int userId = 1;

    public StatisticFragment() {}

    public static StatisticFragment newInstance() {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        etTotalRevenue = view.findViewById(R.id.etTotalRevenue);
        etTotalExpense = view.findViewById(R.id.etTotalExpense);
        rvRevenueTypeStatistic = view.findViewById(R.id.rvRevenueType);
        rvExpenseTypeStatistic = view.findViewById(R.id.rvExpenseType);
        statisticViewModel = new ViewModelProvider(this).get(StatisticViewModel.class);

        userId = ((MainActivity) getActivity()).userID();
        statisticViewModel.setUserId(userId);

        revenueTypeStatisticAdapter = new RevenueTypeStatisticRecyclerView(getActivity());
        rvRevenueTypeStatistic.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRevenueTypeStatistic.setAdapter(revenueTypeStatisticAdapter);

        expenseTypeStatisticAdapter = new ExpenseTypeStatisticRecyclerView(getActivity());
        rvExpenseTypeStatistic.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvExpenseTypeStatistic.setAdapter(expenseTypeStatisticAdapter);
        statisticViewModel.getTotalRevenue().observe(getActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float total) {
                etTotalRevenue.setText(String.valueOf(total));
            }
        });

        statisticViewModel.getTotalExpense().observe(getActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float total) {
                etTotalExpense.setText(String.valueOf(total));
            }
        });

        statisticViewModel.getListRevenueTypeStatistic().observe(getActivity(), new Observer<List<RevenueTypeStatistic>>() {
            @Override
            public void onChanged(List<RevenueTypeStatistic> lsr) {
                revenueTypeStatisticAdapter.setList(lsr);
            }
        });

        statisticViewModel.getListExpenseTypeStatistic().observe(getActivity(), new Observer<List<ExpenseTypeStatistic>>() {
            @Override
            public void onChanged(List<ExpenseTypeStatistic> lst) {
                expenseTypeStatisticAdapter.setList(lst);
            }
        });

        return view;
    }
}