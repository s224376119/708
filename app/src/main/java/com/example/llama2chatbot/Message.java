package com.example.llama2chatbot;

public class Message {
    public static String SENT_BY_ME="me";
    public static String SENT_BY_BOT="bot";

    String messageQuestion,sendBy,messageAnswer;

    public String getMessageAnswer() {
        return messageAnswer;
    }

    public void setMessageAnswer(String messageAnswer) {
        this.messageAnswer = messageAnswer;
    }

    public Message(String messageQuestion, String sendBy, String messageAnswer) {
        this.messageQuestion = messageQuestion;
        this.sendBy = sendBy;
        this.messageAnswer=messageAnswer;
    }

    public Message() {
    }

    public String getMessageQuestion() {
        return messageQuestion;
    }

    public void setMessageQuestion(String messageQuestion) {
        this.messageQuestion = messageQuestion;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }
}
