package com.example.expendituremanagementapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.model.RevenueType;
import com.example.expendituremanagementapp.ui.revenue.RevenueTypeFragment;

import java.util.List;

public class RevenueTypeAdapter extends RecyclerView.Adapter<RevenueTypeAdapter.RevenueTypeViewAdapter>{
    private RevenueTypeFragment context;
    private List<RevenueType> lists;

    public RevenueTypeAdapter(RevenueTypeFragment context, List<RevenueType> lists) {
        this.context = context;
        this.lists = lists;
    }
    public void setData(List<RevenueType> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RevenueTypeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.revenue_type_item, parent, false);
        return new RevenueTypeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueTypeViewAdapter holder, int position) {
        RevenueType revenueType = lists.get(position);
        if(revenueType == null)
            return;
        holder.name.setText(revenueType.getName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(revenueType.getName(), revenueType.getId());
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.edit(revenueType.getName(), revenueType.getId());
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

    public class RevenueTypeViewAdapter extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView edit, delete;
        private RelativeLayout layout;
        public RevenueTypeViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.revenue_type_item_name);
            edit = itemView.findViewById(R.id.revenue_type_item_edit);
            delete = itemView.findViewById(R.id.revenue_type_item_delete);
            layout = itemView.findViewById(R.id.revenue_type_item_layout);
        }
    }
}
