package com.example.expendituremanagementapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.model.ExpenseType;
import com.example.expendituremanagementapp.ui.expense.ExpenseTypeFragment;


import java.util.List;

public class ExpenseTypeAdapter extends RecyclerView.Adapter<ExpenseTypeAdapter.ExpenseTypeViewAdapter>{
    private ExpenseTypeFragment context;
    private List<ExpenseType> lists;

    public ExpenseTypeAdapter(ExpenseTypeFragment context, List<ExpenseType> lists) {
        this.context = context;
        this.lists = lists;
    }
    public void setData(List<ExpenseType> list){
        this.lists = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseTypeAdapter.ExpenseTypeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_type_item, parent, false);
        return new ExpenseTypeAdapter.ExpenseTypeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseTypeAdapter.ExpenseTypeViewAdapter holder, int position) {
        ExpenseType expenseType = lists.get(position);
        if(expenseType == null)
            return;
        holder.name.setText(expenseType.getName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    context.delete(expenseType.getName(), expenseType.getId());
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    context.edit(expenseType.getName(), expenseType.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lists != null)
        {
            return lists.size();
        }
        return 0;
    }

    public class ExpenseTypeViewAdapter extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView edit, delete;
        public ExpenseTypeViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.expense_type_item_name);
            edit = itemView.findViewById(R.id.expense_type_item_edit);
            delete = itemView.findViewById(R.id.expense_type_item_delete);
        }
    }
}
