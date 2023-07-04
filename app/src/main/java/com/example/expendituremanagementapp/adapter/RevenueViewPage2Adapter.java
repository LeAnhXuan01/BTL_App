package com.example.expendituremanagementapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.expendituremanagementapp.ui.revenue.RevenueDetailFragment;
import com.example.expendituremanagementapp.ui.revenue.RevenueTypeFragment;

public class RevenueViewPage2Adapter extends FragmentStateAdapter {
    public RevenueViewPage2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if(position == 0){
            fragment = new RevenueDetailFragment();
        }else {
            fragment = new RevenueTypeFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
