package com.example.quizapp.helpers;

import java.util.ArrayList;
import java.util.List;


public class QuestionsBrain {
    public static List<Questions> getQuestionList() {
        List<Questions> questions = new ArrayList<>();

        // Add questions to the list
        questions.add(new Questions("What is the capital of France?", "A) London", "B) Paris (Correct Answer)", "C) Berlin", 2));
        questions.add(new Questions("Who wrote \"Romeo and Juliet\"?", "A) William Shakespeare (Correct Answer)", "B) Charles Dickens", "C) Jane Austen", 1));
        questions.add(new Questions("What is the chemical symbol for water?", "A) Wa", "B) H2O (Correct Answer)", "C) O2", 2));
        questions.add(new Questions("What is the largest planet in our solar system?", "A) Earth", "B) Mars", "C) Jupiter (Correct Answer)", 3));
        questions.add(new Questions("Who painted the Mona Lisa?", "A) Vincent van Gogh", "B) Leonardo da Vinci (Correct Answer)", "C) Pablo Picasso", 2));
        questions.add(new Questions("Which of the following is a mammal?", "A) Crocodile", "B) Penguin", "C) Dolphin (Correct Answer)", 3));
        questions.add(new Questions("What is the powerhouse of the cell?", "A) Nucleus", "B) Mitochondria (Correct Answer)", "C) Ribosome", 2));
        questions.add(new Questions("What is the chemical symbol for gold?", "A) Au (Correct Answer)", "B) Ag", "C) G", 1));
        questions.add(new Questions("Who discovered gravity?", "A) Isaac Newton (Correct Answer)", "B) Albert Einstein", "C) Galileo Galilei", 1));
        questions.add(new Questions("What is the tallest mountain in the world?", "A) Mount Kilimanjaro", "B) Mount Everest (Correct Answer)", "C) Mount Fuji", 2));
        questions.add(new Questions("What is the currency of Japan?", "A) Yuan", "B) Yen (Correct Answer)", "C) Won", 2));
        questions.add(new Questions("What is the chemical symbol for oxygen?", "A) O (Correct Answer)", "B) Ox", "C) Oc", 1));
        questions.add(new Questions("Who wrote \"To Kill a Mockingbird\"?", "A) J.K. Rowling", "B) Harper Lee (Correct Answer)", "C) Stephen King", 2));
        questions.add(new Questions("What is the largest ocean in the world?", "A) Indian Ocean", "B) Atlantic Ocean", "C) Pacific Ocean (Correct Answer)", 3));
        questions.add(new Questions("What is the capital of Canada?", "A) Toronto", "B) Ottawa (Correct Answer)", "C) Montreal", 2));

        return questions;
    }
}
