package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.helpers.Utils;
import com.example.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.etName.setText(Utils.userName);
        binding.btnStart.setOnClickListener(v -> {
            if (!binding.etName.getText().toString().equals("")
                    && binding.etName.getText().toString() != null) {
                Utils.userName = binding.etName.getText().toString().trim();
                startActivity(new Intent(MainActivity.this, QuestionsActivity.class));
            } else {
                Toast.makeText(MainActivity.this, "Please first enter Your name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}