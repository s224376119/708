package com.example.todotask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailPage extends AppCompatActivity {
    ImageView imageView;
    DatabaseHelper databaseHelper;
    TextView tvName,tvDec, tvTimeStamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        databaseHelper =new DatabaseHelper(getApplicationContext());
        tvName = findViewById(R.id.tvName);
        tvDec = findViewById(R.id.tvDec);
        tvTimeStamp = findViewById(R.id.tvQuantity);

        TaskModel model = (TaskModel) getIntent().getSerializableExtra("data");

        tvName.setText(model.getName());
        tvDec.setText(model.getDic());
        tvTimeStamp.setText(formatDate(model.getCurrentTimeStamp()));




    }
    public String formatDate(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(timeStamp);
        return sdf.format(date);
    }
}