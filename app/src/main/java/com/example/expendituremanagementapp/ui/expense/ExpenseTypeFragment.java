package com.example.expendituremanagementapp.ui.expense;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.expendituremanagementapp.adapter.ExpenseTypeAdapter;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.model.ExpenseType;

import java.util.ArrayList;

public class ExpenseTypeFragment extends Fragment {

    private ExpenseTypeViewModel mViewModel;
    private RecyclerView rcV;
    private ExpenseTypeAdapter adapter;
    private ArrayList<ExpenseType> arrayList;
    private TextView tvAdd;
    private DatabaseHelper database;

    private int userId = 1;

    public static ExpenseTypeFragment newInstance() {
        return new ExpenseTypeFragment();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcV = view.findViewById(R.id.rcV_expense_type);
        arrayList = new ArrayList<>();
        adapter = new ExpenseTypeAdapter(this, arrayList);
        tvAdd = view.findViewById(R.id.tv_expense_type_add);

        userId = ((MainActivity) getActivity()).userID();

        database = new DatabaseHelper(view.getContext());
        SQLiteDatabase db = database.getWritableDatabase();

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_expense_type_add);
                Window window = dialog.getWindow();
                if(window != null){
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                EditText edtName = dialog.findViewById(R.id.edt_expense_type_add_name);
                Button btnAdd = dialog.findViewById(R.id.btn_expense_type_add);
                Button btnCanel = dialog.findViewById(R.id.btn_expense_type_add_canel);
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
                        db.execSQL("INSERT INTO expense_types VALUES(null, '"+name+"', '"+userId+"')");
                        dialog.dismiss();
                        actionGetData();
                        Toast.makeText(view.getContext(), "Added successfully!", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        rcV.setLayoutManager(linearLayoutManager);
        adapter.setData(arrayList);
        rcV.setAdapter(adapter);

        actionGetData();
    }

    private void actionGetData(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM expense_types", null);
        arrayList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int userId = cursor.getInt(2);
            arrayList.add(new ExpenseType(id, userId, name));
            adapter.notifyDataSetChanged();
        }
    }

    public void delete(String name, int i){
        AlertDialog.Builder dialog =new AlertDialog.Builder(getActivity());
        dialog.setTitle("Delete");
        dialog.setMessage("Are you sure you want to delete "+name+" ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = database.getWritableDatabase();
                db.execSQL("DELETE FROM expense_types WHERE id = '"+i+"'");
                actionGetData();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        return;
    }
    public void edit(String ten, int i){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_expense_type_edit);
        Window window = dialog.getWindow();
        if(window != null){
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        EditText edtName = dialog.findViewById(R.id.edt_expense_type_edit_name);
        Button btnEdit = dialog.findViewById(R.id.btn_expense_type_edit);
        Button btnCanel = dialog.findViewById(R.id.btn_expense_type_edit_canel);

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
//                Toast.makeText(v.getContext(), "Chỉnh sửa", Toast.LENGTH_SHORT).show();
                if(name.isEmpty()){
                    Toast.makeText(v.getContext(), "You must enter full data!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                else {
                    SQLiteDatabase db = database.getWritableDatabase();
                    db.execSQL("UPDATE expense_types SET name='"+name+"' WHERE id='"+i+"'");
                    dialog.dismiss();
                    actionGetData();
                }
            }
        });
        dialog.show();
    }
    private boolean check(int i){
        SQLiteDatabase db1 = database.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT id FROM expense_types", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            if ( i == id)
                return false;
        }
        return true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expense_type, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ExpenseTypeViewModel.class);
        // TODO: Use the ViewModel
    }

}