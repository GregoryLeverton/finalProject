package com.example.greg.quizgame;

/**
 * Created by Greg on 2018-04-10.
 */

public class MCQuestion extends Question {
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correctAnswer;

    public MCQuestion(String question, String answer1, String answer2, String answer3, String answer4, int correct){
        setQuestion(question);
        setAnswer1(answer1);
        setAnswer2(answer2);
        setAnswer3(answer3);
        setAnswer4(answer4);
        setCorrectAnswer(correct);
        type = 1;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
