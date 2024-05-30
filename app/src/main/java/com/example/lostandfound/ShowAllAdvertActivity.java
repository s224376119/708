package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class ShowAllAdvertActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Post>list;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_advert);

    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseHelper=new DatabaseHelper(getApplicationContext());
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        list=databaseHelper.getAllPosts();
        LostAndFoundAdapter adapter=new LostAndFoundAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}