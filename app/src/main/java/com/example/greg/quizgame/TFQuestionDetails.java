package com.example.greg.quizgame;
/**
 * Created by Greg Leverton on 2018-03-30.
 */

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.greg.finalproject.R;

public class TFQuestionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tf_question_details);


        Bundle bundle = getIntent().getBundleExtra("QuestionItem");
        TFQuestionFragment myFragment = new TFQuestionFragment();
        myFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, myFragment).commit();
    }
}