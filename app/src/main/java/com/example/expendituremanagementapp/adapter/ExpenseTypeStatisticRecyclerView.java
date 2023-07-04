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
import com.example.expendituremanagementapp.model.ExpenseTypeStatistic;

import java.util.List;

public class ExpenseTypeStatisticRecyclerView extends RecyclerView.Adapter<ExpenseTypeStatisticRecyclerView.ExpenseTypeStatisticViewHolder> {
    private LayoutInflater layoutInflater;
    private List<ExpenseTypeStatistic> lsr;

    public ExpenseTypeStatisticRecyclerView(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExpenseTypeStatisticRecyclerView.ExpenseTypeStatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_expense_statistic, parent, false);

        return new ExpenseTypeStatisticRecyclerView.ExpenseTypeStatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseTypeStatisticRecyclerView.ExpenseTypeStatisticViewHolder holder, int position) {
        if (lsr != null) {
            holder.tvExpenseType.setText(lsr.get(position).getName());
            holder.etTotalExpenseType.setText(lsr.get(position).getTotal().toString());
        }
    }

    @Override
    public int getItemCount() {
        if (lsr != null)
            return lsr.size();
        return 0;
    }

    public void setList(List<ExpenseTypeStatistic> lsr) {
        this.lsr = lsr;
        notifyDataSetChanged();
    }

    public static class ExpenseTypeStatisticViewHolder extends RecyclerView.ViewHolder {
        private TextView tvExpenseType;
        private EditText etTotalExpenseType;

        public ExpenseTypeStatisticViewHolder(@NonNull View itemView) {
            super(itemView);

            tvExpenseType = itemView.findViewById(R.id.tvExpenseType);
            etTotalExpenseType = itemView.findViewById(R.id.etTotalExpenseType);
        }
    }
}
