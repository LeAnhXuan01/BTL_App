package com.example.expendituremanagementapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.expendituremanagementapp.ui.expense.ExpenseDetailFragment;
import com.example.expendituremanagementapp.ui.expense.ExpenseTypeFragment;


public class ExpenseViewPage2Adapter extends FragmentStateAdapter {
    public ExpenseViewPage2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if(position == 0){
            fragment = new ExpenseDetailFragment();
        }else {
            fragment = new ExpenseTypeFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
