package com.example.expendituremanagementapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.model.Expense;
import com.example.expendituremanagementapp.ui.expense.ExpenseDetailFragment;

import java.util.List;

public class ExpenseAdapter extends  RecyclerView.Adapter<ExpenseAdapter.ExpenseViewAdapter> {
    private ExpenseDetailFragment context;
    private List<Expense> lists;

    public ExpenseAdapter(ExpenseDetailFragment context, List<Expense> lists) {
        this.context = context;
        this.lists = lists;
    }
    public void setData(List<Expense> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ExpenseAdapter.ExpenseViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_detail_item, parent, false);
        return new ExpenseAdapter.ExpenseViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewAdapter holder, int position) {
        Expense expense = lists.get(position);
        if (expense == null)
            return;
        holder.name.setText(expense.getName());
        holder.price.setText(expense.getPrice() + "");

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(expense.getId());
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expense.getNote().isEmpty()){
                    expense.setNote("You don't have note!");
                }

                AlertDialog.Builder buider = new AlertDialog.Builder(v.getContext());
                buider.setTitle("Information");
                buider.setMessage("Name: "+expense.getName()+"\nPrice: "+expense.getPrice()+"\nNote: "+expense.getNote()+"\nThời gian: "+expense.getDate());
                buider.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                buider.create().show();
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

    public class ExpenseViewAdapter extends RecyclerView.ViewHolder{
        private TextView name, note, price;
        private LinearLayout layout;
        private ImageButton btn;
        public ExpenseViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.expense_detail_name);
            price = itemView.findViewById(R.id.expense_detail_price);
            btn = itemView.findViewById(R.id.btn_expense_item_delete);
            layout = itemView.findViewById(R.id.expense_item_layout);
        }
    }
}
