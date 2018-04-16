package com.example.greg.quizgame;

/**
 * Created by Greg Leverton on 2018-03-25.
 */

public class TFQuestion extends Question {

    private boolean answer;
    //no-arg constructor
    public TFQuestion() {
        type = 2;
    }
    //constructor
    public TFQuestion(String question, boolean answer) {
        setQuestion(question);
        type = 2;
        setAnswer(answer);
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
