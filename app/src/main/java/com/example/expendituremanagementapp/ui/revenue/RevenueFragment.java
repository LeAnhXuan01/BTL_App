package com.example.expendituremanagementapp.ui.revenue;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.adapter.RevenueViewPage2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueFragment extends Fragment {
    private TabLayout mTl;
    private ViewPager2 mVp;

    public RevenueFragment() {
        // Required empty public constructor
    }

    public static RevenueFragment newInstance(String param1, String param2) {
        RevenueFragment fragment = new RevenueFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTl = view.findViewById(R.id.tab_Layout);
        mVp = view.findViewById(R.id.view_Pager2);

        RevenueViewPage2Adapter adapter = new RevenueViewPage2Adapter(getActivity());
        mVp.setAdapter(adapter);

        new TabLayoutMediator(mTl, mVp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position  == 0){
                    tab.setText("Revenue Detail");
                    tab.setIcon(R.drawable.baseline_library_books_24);
                }
                else{
                    tab.setText("Revenue Type");
                    tab.setIcon(R.drawable.ic_menu_camera);
                }
            }
        }).attach();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_revenue, container, false);
    }
}