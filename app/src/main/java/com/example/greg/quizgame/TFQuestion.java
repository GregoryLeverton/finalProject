package com.example.greg.quizgame;

/**
 * Created by Greg on 2018-04-10.
 */

public class TFQuestion extends Question {

    private boolean answer;

    public TFQuestion() {
        type = 2;
    }

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
