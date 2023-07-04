package com.example.expendituremanagementapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.model.RevenueTypeStatistic;

import java.util.List;

public class RevenueTypeStatisticRecyclerView extends RecyclerView.Adapter<RevenueTypeStatisticRecyclerView.RevenueTypeStatisticViewHolder> {
    private LayoutInflater layoutInflater;
    private List<RevenueTypeStatistic> lsr;

    public RevenueTypeStatisticRecyclerView(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RevenueTypeStatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_revenue_statistic, parent, false);

        return new RevenueTypeStatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueTypeStatisticViewHolder holder, int position) {
        if (lsr != null) {
            holder.tvRevenueType.setText(lsr.get(position).getName());
            holder.etTotalRevenueType.setText(lsr.get(position).getTotal().toString());
        }
    }

    @Override
    public int getItemCount() {
        if (lsr != null)
            return lsr.size();
        return 0;
    }

    public void setList(List<RevenueTypeStatistic> lsr) {
        this.lsr = lsr;
        notifyDataSetChanged();
    }

    public static class RevenueTypeStatisticViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRevenueType;
        private EditText etTotalRevenueType;

        public RevenueTypeStatisticViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRevenueType = itemView.findViewById(R.id.tvRevenueType);
            etTotalRevenueType = itemView.findViewById(R.id.etTotalRevenueType);
        }
    }
}
