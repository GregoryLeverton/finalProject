package com.example.greg.quizgame;


/**
 * Created by Greg on 2018-03-25.
 */

public class NumericQuestion extends Question {
    private double answer;
    private int precision;

    //no-arg constructor
    public NumericQuestion() {
        type = 3;
    }

    //constructor
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

}
