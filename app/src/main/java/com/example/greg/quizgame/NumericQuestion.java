package com.example.greg.quizgame;


/**
 * Created by Greg on 2018-04-10.
 */

public class NumericQuestion extends Question {
    private double answer;
    private int precision;

    public NumericQuestion() {
        type = 3;
    }

    public NumericQuestion(String Question, double answer, int precision) {
        setQuestion(Question);
        setAnswer(answer);
        setPrecision(precision);
        type = 3;
    }

    public double getAnswer() {
        return answer;
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public boolean isCorrect(double attempt) {
        return (Math.abs(getAnswer() - attempt) < Math.pow(10, -getPrecision()));

    }
}
