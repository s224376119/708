package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.helpers.Utils;
import com.example.quizapp.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvCongratulation.setText(Utils.userName + "!");
        binding.tvScore.setText(Utils.gotScore + "/" + Utils.totalQuestions);

        binding.btnNewQuiz.setOnClickListener(v -> {
            Utils.gotScore = 0;
            startActivity(new Intent(ResultActivity.this, MainActivity.class));
            finish();
        });
        binding.btnFinish.setOnClickListener(v -> {
            Utils.clearData();
            finishAffinity();
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.gotScore=0;
        finish();
    }


}