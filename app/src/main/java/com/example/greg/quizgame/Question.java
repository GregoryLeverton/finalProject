package com.example.greg.quizgame;

/**
 * Created by Greg on 2018-04-10.
 */

public abstract class Question {
    protected int type; //type 1 for multiple choice, 2 for T/F, 3 for numeric
    protected String question;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getType(){
        return type;
    }


}
