package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdvertDetailActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView tvName,tvDate,tvDescription,tvLocation;
    Button btn_remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_detail_page);
        tvDescription=findViewById(R.id.tvDescription);
        tvName=findViewById(R.id.tvName);
        tvDate=findViewById(R.id.tvDate);
        tvLocation=findViewById(R.id.tvLocation);
        btn_remove=findViewById(R.id.btn_remove);

        Post post= (Post) getIntent().getSerializableExtra("data");

        tvDescription.setText(post.getDescription());
        tvName.setText(post.getName());
        tvDate.setText(post.getDate());
        tvLocation.setText(post.getLocation());

        btn_remove.setOnClickListener(v->{

        databaseHelper=new DatabaseHelper(AdvertDetailActivity.this);
       boolean check= databaseHelper.deletePost(post.getKey());
       if(check==true)
       {
           //Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
           //Intent intent=new Intent(this,ShowAllAdvertActivity.class);
           //startActivity(intent);
           Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
           finish();
       }
       else {
           Toast.makeText(this, "No deleted", Toast.LENGTH_SHORT).show();
       }

        });



    }
}