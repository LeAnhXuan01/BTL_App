package com.example.expendituremanagementapp.ui.revenue;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expendituremanagementapp.MainActivity;
import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.adapter.RevenueTypeAdapter;
import com.example.expendituremanagementapp.model.RevenueType;

import java.util.ArrayList;

public class RevenueTypeFragment extends Fragment {
    private RevenueTypeViewModel mViewModel;
    private RecyclerView rcV;
    private RevenueTypeAdapter adapter;
    private TextView tvAdd;
    private DatabaseHelper database;
    //truyền serID vào đây
    private int userId = 1;

    public static RevenueTypeFragment newInstance() {
        return new RevenueTypeFragment();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcV = view.findViewById(R.id.rcV_revenue_type);
        tvAdd = view.findViewById(R.id.tv_revenue_type_add);

        userId = ((MainActivity) getActivity()).userID();

        database = new DatabaseHelper(view.getContext());

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_revenue_type_add);
                Window window = dialog.getWindow();
                if(window != null){
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                EditText edtName = dialog.findViewById(R.id.edt_revenue_type_add_name);
                Button btnAdd = dialog.findViewById(R.id.btn_revenue_type_add);
                Button btnCanel = dialog.findViewById(R.id.btn_revenue_type_add_cancel);
                btnCanel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtName.getText().toString().trim();
                        if(name.isEmpty()){
                            Toast.makeText(view.getContext(), "You didn't type anything!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return;
                        }
                        Toast.makeText(view.getContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                        database.insert_Revenue_Type(name, userId);
                        adapter.setData(getList());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                    dialog.show();
            }
        });

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        rcV.addItemDecoration(decoration);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        rcV.setLayoutManager(linearLayoutManager);
        adapter = new RevenueTypeAdapter(this, getList());
        rcV.setAdapter(adapter);
    }

    private ArrayList<RevenueType> getList(){
        ArrayList<RevenueType> arrayList = new ArrayList<>();
        Cursor cursor1 = database.select("revenue_types", userId);
        while (cursor1.moveToNext()){
            int id = cursor1.getInt(0);
            String name = cursor1.getString(1);
            int userId = cursor1.getInt(2);
            arrayList.add(new RevenueType(id, userId, name));
        }
        return arrayList;
    }

    public void delete(String name, int id){
        AlertDialog.Builder dialog =new AlertDialog.Builder(getActivity());
        dialog.setTitle("Delete");
        dialog.setMessage("Are you sure you want to delete "+name+" ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.delete_Revenue("revenue_types", id);
                Toast.makeText(getContext(), "Deleted revenue type successfully!", Toast.LENGTH_SHORT).show();
                adapter.setData(getList());
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("No    ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.create().show();
    }
    public void edit(String ten, int id){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_revenue_type_edit);
        Window window = dialog.getWindow();
        if(window != null){
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        EditText edtName = dialog.findViewById(R.id.edt_revenue_type_edit_name);
        Button btnEdit = dialog.findViewById(R.id.btn_revenue_type_edit);
        Button btnCanel = dialog.findViewById(R.id.btn_revenue_type_edit_cancel);

        edtName.setText(ten);
        btnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                if(name.isEmpty()){
                    Toast.makeText(v.getContext(), "You have to type data", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                else {
                    RevenueType revenueType = new RevenueType(id, name);
                    database.update_Revenue_Type(revenueType);
                    Toast.makeText(getContext(), "Edited successfully!", Toast.LENGTH_SHORT).show();
                    adapter.setData(getList());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_revenue_type, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RevenueTypeViewModel.class);
        // TODO: Use the ViewModel
    }
}