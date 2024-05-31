package com.example.simplecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
Button btnAdd, btnSubtract;
EditText etFValue, etSValue;
TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

btnAdd= findViewById(R.id.addBtn);
btnSubtract= findViewById(R.id.subtractBtn);
etFValue= findViewById(R.id.FValue);
etSValue= findViewById(R.id.SValue);
tvResult= findViewById(R.id.tvValue);


btnAdd.setOnClickListener(v-> {
    String fvalue = etFValue.getText().toString().trim();
    String svalue = etSValue.getText().toString().trim();

    if (fvalue.isEmpty()){
        Toast.makeText(this, "Please enter First value", Toast.LENGTH_SHORT).show();
    }
    else if (svalue.isEmpty()) {

        Toast.makeText(this, "Please enter Second value", Toast.LENGTH_SHORT).show();
    }
    else {
        double sum = Double.parseDouble(fvalue) + Double.parseDouble(svalue);

        tvResult.setText(String.format("%.2f", sum));
        etSValue.setText("");
        etFValue.setText("");
    }
});


btnSubtract.setOnClickListener(v-> {
    String fvalue = etFValue.getText().toString().trim();
    String svalue = etSValue.getText().toString().trim();

    if (fvalue.isEmpty()){
        Toast.makeText(this, "Please enter First value", Toast.LENGTH_SHORT).show();
    }
    else if (svalue.isEmpty()) {

        Toast.makeText(this, "Please enter Second value", Toast.LENGTH_SHORT).show();
    }
    else {
        double result = Double.parseDouble(fvalue) - Double.parseDouble(svalue);;

        tvResult.setText(String.format("%.2f", result));
        etSValue.setText("");
        etFValue.setText("");
    }
});



    }
}