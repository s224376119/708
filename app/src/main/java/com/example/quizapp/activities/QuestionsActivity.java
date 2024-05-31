package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.helpers.QuestionsBrain;
import com.example.quizapp.R;
import com.example.quizapp.helpers.Utils;
import com.example.quizapp.databinding.ActivityQuestionsBinding;

public class QuestionsActivity extends AppCompatActivity {
    private ActivityQuestionsBinding binding;
    private int questionNo = 0;
    private int selectedOption;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvWelcome.setText("Welcome " + Utils.userName + "!");
        Utils.totalQuestions = QuestionsBrain.getQuestionList().size();
        binding.tvTotalQuestions.setText("/" + Utils.totalQuestions);

        setQuestionData();

        binding.btnSubmit.setOnClickListener(v -> {
            onSubmitButtonClicked();
        });
        binding.btnNext.setOnClickListener(v -> {
            ++questionNo;
            if (questionNo == Utils.totalQuestions) {
                binding.btnNext.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                startActivity(new Intent(QuestionsActivity.this, ResultActivity.class));
            }
            else
            {
                setQuestionData();
            }
        });

        binding.tvAnsA.setOnClickListener(v -> {
            onAnswerButtonClicked(1);
            binding.btnSubmit.setEnabled(true);
        });
        binding.tvAnsB.setOnClickListener(v -> {
            onAnswerButtonClicked(2);
            binding.btnSubmit.setEnabled(true);
        });
        binding.tvAnsC.setOnClickListener(v -> {
            onAnswerButtonClicked(3);
            binding.btnSubmit.setEnabled(true);

        });
    }

    private void setQuestionData() {
        defaultSetting();
        binding.tvQuestionNo.setText((1 + questionNo) + "");
        setProgressBar();

        binding.tvQuestionString.setText(QuestionsBrain.getQuestionList().get(questionNo).getQuestionString());
        binding.tvAnsA.setText(QuestionsBrain.getQuestionList().get(questionNo).getOptionA());
        binding.tvAnsB.setText(QuestionsBrain.getQuestionList().get(questionNo).getOptionB());
        binding.tvAnsC.setText(QuestionsBrain.getQuestionList().get(questionNo).getOptionC());
    }

    private void onAnswerButtonClicked(int option) {
        binding.tvAnsA.setBackgroundResource(R.drawable.tv1_bg);
        binding.tvAnsB.setBackgroundResource(R.drawable.tv1_bg);
        binding.tvAnsC.setBackgroundResource(R.drawable.tv1_bg);

        switch (option) {
            case 1:
                binding.tvAnsA.setBackgroundResource(R.drawable.tv_selected_bg);
                selectedOption = 1;
                break;
            case 2:
                binding.tvAnsB.setBackgroundResource(R.drawable.tv_selected_bg);
                selectedOption = 2;
                break;
            case 3:
                binding.tvAnsC.setBackgroundResource(R.drawable.tv_selected_bg);
                selectedOption = 3;
                break;
        }
    }

    private void onSubmitButtonClicked() {
        binding.btnSubmit.setEnabled(false);
        binding.btnSubmit.setVisibility(View.GONE);
        binding.btnNext.setVisibility(View.VISIBLE);
        int correctOption = QuestionsBrain.getQuestionList().get(questionNo).getCorrectAnswer();

        if (correctOption == selectedOption) {
            ++Utils.gotScore;
        }

        switch (correctOption) {
            case 1:
                binding.tvAnsA.setBackgroundResource(R.drawable.tv_correct_bg);
                if (selectedOption == 2) {
                    binding.tvAnsB.setBackgroundResource(R.drawable.tv_wrong_bg);
                }
                if (selectedOption == 3) {
                    binding.tvAnsC.setBackgroundResource(R.drawable.tv_wrong_bg);
                }
                break;
            case 2:
                binding.tvAnsB.setBackgroundResource(R.drawable.tv_correct_bg);
                if (selectedOption == 1) {
                    binding.tvAnsA.setBackgroundResource(R.drawable.tv_wrong_bg);
                }
                if (selectedOption == 3) {
                    binding.tvAnsC.setBackgroundResource(R.drawable.tv_wrong_bg);
                }
                break;
            case 3:
                binding.tvAnsC.setBackgroundResource(R.drawable.tv_correct_bg);
                if (selectedOption == 1) {
                    binding.tvAnsA.setBackgroundResource(R.drawable.tv_wrong_bg);
                }
                if (selectedOption == 2) {
                    binding.tvAnsB.setBackgroundResource(R.drawable.tv_wrong_bg);
                }
                break;
        }

    }

    private void defaultSetting() {
        binding.btnNext.setVisibility(View.GONE);
        binding.btnSubmit.setVisibility(View.VISIBLE);
        binding.btnSubmit.setEnabled(false);

        binding.tvAnsA.setBackgroundResource(R.drawable.tv1_bg);
        binding.tvAnsB.setBackgroundResource(R.drawable.tv1_bg);
        binding.tvAnsC.setBackgroundResource(R.drawable.tv1_bg);

    }

    private void setProgressBar() {
        // Reset progress status
//        binding.pbQuestions.setProgress((1+questionNo));

        // Start counting
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (questionNo < 15) {
                    binding.pbQuestions.setProgress((1 + questionNo));
//                    handler.postDelayed(this, 1000); // Delay 1 second
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.gotScore=0;
        finish();
    }
}