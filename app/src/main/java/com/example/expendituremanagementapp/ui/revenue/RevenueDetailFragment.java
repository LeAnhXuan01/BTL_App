package com.example.expendituremanagementapp.ui.revenue;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expendituremanagementapp.MainActivity;
import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.adapter.RevenueAdapter;
import com.example.expendituremanagementapp.model.Revenue;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class RevenueDetailFragment extends Fragment {

    private RevenueDetailViewModel mViewModel;
    private RecyclerView rcV;
    private RevenueAdapter adapter;
    private DatabaseHelper database;
    private TextView tvAdd, tvTotal;
    private int userId = 1;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_revenue_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcV = view.findViewById(R.id.rcV_revenue_detail);
        tvAdd = view.findViewById(R.id.tv_revenue_add);
        tvTotal = view.findViewById(R.id.tv_revenue_total);
        database = new DatabaseHelper(view.getContext());

        userId = ((MainActivity) getActivity()).userID();
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_revenue_add);
                Window window = dialog.getWindow();
                if(window != null){
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                AutoCompleteTextView auto_name = dialog.findViewById(R.id.auto_revenue_add_name);
                EditText edtPrice = dialog.findViewById(R.id.edt_revenue_add_price);
                EditText edtNote = dialog.findViewById(R.id.edt_revenue_add_note);
                EditText edtDate = dialog.findViewById(R.id.edt_revenue_add_date);
                Button btnAdd = dialog.findViewById(R.id.btn_revenue_add);
                Button btnCancel = dialog.findViewById(R.id.btn_revenue_add_cancel);

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_dropdown_item_1line, optionName());
                auto_name.setAdapter(adapter1);
                auto_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        auto_name.showDropDown();
                    }
                });

                final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                final SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                LocalDate currentDate = LocalDate.now();
                edtDate.setText(currentDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Xử lý ngày được chọn
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, monthOfYear, dayOfMonth);
                        // Hiển thị giá trị ngày được chọn trên EditText
                        edtDate.setText(inputDateFormat.format(selectedCalendar.getTime()));
                    }
                }, year, month, day);
                edtDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.show();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = auto_name.getText().toString().trim();
                        String note = edtNote.getText().toString().trim();
                        String date = edtDate.getText().toString().trim();
                        if(name.isEmpty() || edtPrice.getText().toString().trim().isEmpty()){
                            Toast.makeText(view.getContext(), "You must enter full data!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            float price = Float.parseFloat(edtPrice.getText().toString().trim());
                            Revenue revenue = new Revenue(name, price, note, date, userId, getTypeID(name));
                            database.insert_Revenue(revenue);
                            Toast.makeText(view.getContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                            adapter.setData(getList());
                            adapter.notifyDataSetChanged();
                            tvTotal.setText(getPriceTotal() + "");
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });

        tvTotal.setText(getPriceTotal()+"");

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        rcV.addItemDecoration(decoration);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        rcV.setLayoutManager(linearLayoutManager);
        adapter = new RevenueAdapter(this, getList());
        rcV.setAdapter(adapter);
    }
    private ArrayList<Revenue> getList() {
        ArrayList<Revenue> list = new ArrayList<>();
        Cursor cursor = database.select("revenues", userId);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            float price = cursor.getFloat(2);
            String note = cursor.getString(3);
            String date = cursor.getString(4);
            int userId = cursor.getInt(5);
            int revenueTypeId = cursor.getInt(6);
            list.add(new Revenue(id, name, price, note, date, userId, revenueTypeId));
        }
        return list;
    }
    private String[] optionName(){
        String[] option = new String[0];
        Cursor cursor = database.select("revenue_types", userId);
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            option = Arrays.copyOf(option, option.length + 1);
            option[option.length -1] = name;
        }
        return option;
    }
    private int getTypeID(String name){
        Cursor cursor = database.select("revenue_types", userId);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String typeName = cursor.getString(1);
            if (name.equals(typeName))
                return id;
        }
        return -1;
    }
    public void delete(int id){
        AlertDialog.Builder dialog =new AlertDialog.Builder(getActivity());
        dialog.setTitle("Delete");
        dialog.setMessage("Are you sure you want to delete this revenue?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.delete_Revenue("revenues", id);
                adapter.setData(getList());
                adapter.notifyDataSetChanged();
                tvTotal.setText(getPriceTotal() + "");
                Toast.makeText(getActivity(), "Successful delete", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.create().show();
        return;
    }
    private float getPriceTotal() {
        float total = 0f;
        Cursor cursor = database.select("revenues", userId);
        while (cursor.moveToNext()) {
            float price = cursor.getFloat(2);
            total += price;
        }
        return total;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RevenueDetailViewModel.class);
        // TODO: Use the ViewModel
    }
}