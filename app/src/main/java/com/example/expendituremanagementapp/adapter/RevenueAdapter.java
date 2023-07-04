package com.example.expendituremanagementapp.adapter;

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
import com.example.expendituremanagementapp.model.Revenue;
import com.example.expendituremanagementapp.ui.revenue.RevenueDetailFragment;

import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewAdapter>{
    private RevenueDetailFragment context;
    private List<Revenue> lists;

    public RevenueAdapter(RevenueDetailFragment context, List<Revenue> lists) {
        this.context = context;
        this.lists = lists;
    }
    public void setData(List<Revenue> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RevenueViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.revenue_detail_item, parent, false);
        return new RevenueViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewAdapter holder, int position) {
        Revenue revenue = lists.get(position);
        if (revenue == null)
            return;
        holder.name.setText(revenue.getName());
        holder.price.setText(revenue.getPrice() + "");

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(revenue.getId());
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(revenue.getNote().isEmpty()){
                    revenue.setNote("You don't have note!");
                }

                AlertDialog.Builder buider = new AlertDialog.Builder(v.getContext());
                buider.setTitle("Information");
                buider.setMessage("Name: "+ revenue.getName()+"\nPrice: "+ revenue.getPrice()+"\nNote: "+ revenue.getNote()+"\nThời gian: "+ revenue.getDate());
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

    public class RevenueViewAdapter extends RecyclerView.ViewHolder{
        private TextView name, note, price;
        private LinearLayout layout;
        private ImageButton btn;
        public RevenueViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.revenue_detail_name);
            price = itemView.findViewById(R.id.revenue_detail_price);
            btn = itemView.findViewById(R.id.btn_revenue_item_delete);
            layout = itemView.findViewById(R.id.revenue_item_layout);
        }
    }
}
