package com.example.todotask;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HomeFragment extends Fragment implements OnClickEditData{

    AdapterTask adapterTask;
    DatabaseHelper databaseHelper;
    List<TaskModel>taskModels;
    RecyclerView recyclerView;
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            databaseHelper = new DatabaseHelper(getContext());
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        taskModels=databaseHelper.getAllPosts();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapterTask =new AdapterTask(taskModels,getContext(),this);
        recyclerView.setAdapter(adapterTask);

        return  view;
    }

    @Override
    public void onclick(TaskModel model) {
        startActivity(new Intent(getContext(),EditData.class).putExtra("data",model));
    }
}