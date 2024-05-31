package com.example.quizapp.helpers;

public class Questions {
    private String questionString;
    private String optionA;
    private String optionB;
    private String optionC;
    private int correctAnswer;

    public Questions(String questionString, String optionA, String optionB, String optionC, int correctAnswer) {
        this.questionString = questionString;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.correctAnswer = correctAnswer;
    }

    // Getters
    public String getQuestionString() {
        return questionString;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}