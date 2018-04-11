package com.example.greg.quizgame;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.greg.finalproject.R;

public class TFQuestionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mc_question_details);

        Bundle bundle = getIntent().getBundleExtra("QuestionItem");
        TFQuestionFragment myFragment = new TFQuestionFragment();
        myFragment.setArguments( bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, myFragment).commit();
    }
}