package com.example.todotask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditData extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    TextView tvName, tvDic, tvDate;
    Button btnSubmit;
    TaskModel taskModel;
    long timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        tvName = findViewById(R.id.tvName);
        tvDic = findViewById(R.id.tvDic);
        tvDate = findViewById(R.id.tvDate);
        btnSubmit = findViewById(R.id.tvSubmit);

        taskModel = (TaskModel) getIntent().getSerializableExtra("data");

        tvName.setText(taskModel.getName());
        tvDic.setText(taskModel.getDic());
        tvDate.setText(formatDate(taskModel.getCurrentTimeStamp()));
        databaseHelper = new DatabaseHelper(EditData.this);
        tvDate.setOnClickListener(v -> {
            openDatePicker();
        });


        btnSubmit.setOnClickListener(v -> {
            String name, dec, date;
            name = tvName.getText().toString();
            dec = tvDic.getText().toString();
            date = tvDate.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getApplicationContext(), "please fill this field name", Toast.LENGTH_SHORT).show();
                return;
            } else if (dec.isEmpty()) {
                Toast.makeText(getApplicationContext(), "please fill this field description", Toast.LENGTH_SHORT).show();
                return;
            } else if (date.isEmpty()) {
                Toast.makeText(getApplicationContext(), "please select date", Toast.LENGTH_SHORT).show();
                return;
            }
            TaskModel model;
            boolean result;
            model = new TaskModel(name, dec);
            model.setCurrentTimeStamp(timestamp);
            model.setTaskKey(taskModel.getTaskKey());
            result = databaseHelper.updatePost(model);
            if (result) {
                Toast.makeText(getApplicationContext(), "successfully added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finishAffinity();
            } else {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finishAffinity();
    }

    private void openDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditData.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);


                timestamp = calendar.getTimeInMillis();


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = sdf.format(new Date(timestamp));
                tvDate.setText(formattedDate);
            }
        }, 2023, 01, 20);

        datePickerDialog.show();
    }
    public String formatDate(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(timeStamp);
        return sdf.format(date);
    }
}