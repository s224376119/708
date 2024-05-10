package com.example.todotask;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFragment extends Fragment {

    DatabaseHelper databaseHelper;
    TextView tvName, tvDic, tvDate;
    Button btnSubmit;
    long timestamp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        tvName = view.findViewById(R.id.tvName);
        tvDic = view.findViewById(R.id.tvDic);
        tvDate = view.findViewById(R.id.tvDate);
        btnSubmit = view.findViewById(R.id.tvSubmit);

        databaseHelper = new DatabaseHelper(getContext());

        tvDate.setOnClickListener(v -> {
            openDatePicker();
        });
        btnSubmit.setOnClickListener(v -> {
            String name, dec,date;
            name = tvName.getText().toString();
            dec = tvDic.getText().toString();
            date = tvDate.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(getContext(), "please fill this field name", Toast.LENGTH_SHORT).show();
                return;
            } else if (dec.isEmpty()) {
                Toast.makeText(getContext(), "please fill this field description", Toast.LENGTH_SHORT).show();
                return;
            }else if(date.isEmpty()){
                Toast.makeText(getContext(), "please select the date", Toast.LENGTH_SHORT).show();
                return;
            }
            TaskModel model = new TaskModel(name, dec);
            model.setCurrentTimeStamp(timestamp);
            boolean result = databaseHelper.addPost(model);
            if (result) {
                Toast.makeText(getContext(), "successfully added", Toast.LENGTH_SHORT).show();
                clearViews();
            } else {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void openDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);


                timestamp = calendar.getTimeInMillis();


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = sdf.format(new Date(timestamp));

                // Set the formatted date to your TextView or use it as needed
                tvDate.setText(formattedDate);
            }
        }, 2023, 01, 20);

        datePickerDialog.show();
    }
    private void clearViews()
    {
        tvName.setText("");
        tvDic.setText("");
        tvDate.setText("");
    }

}