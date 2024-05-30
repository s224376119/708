package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_createAdv,btn_showAll,btn_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_createAdv=findViewById(R.id.btn_createAdv);
        btn_showAll=findViewById(R.id.btn_showAll);


        btn_createAdv.setOnClickListener(v->{
            Intent intent=new Intent(MainActivity.this,CreateNewAdvertActivity.class);
            startActivity(intent);
        });

        btn_showAll.setOnClickListener(v->{
            Intent intent=new Intent(MainActivity.this,ShowAllAdvertActivity.class);
            startActivity(intent);

        });



    }
}