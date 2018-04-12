package com.example.greg.quizgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.greg.finalproject.R;


public class QuestionStatsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_question_stats);
        TextView total = (TextView) findViewById(R.id.total);
        TextView mc = (TextView) findViewById(R.id.MC);
        TextView tf = (TextView) findViewById(R.id.TF);
        TextView num = (TextView) findViewById(R.id.Num);
        Button back = (Button) findViewById(R.id.back);

        Bundle bundle = getIntent().getBundleExtra("StatsItem");
        int tot = bundle.getInt("tot");
        int m = bundle.getInt("mc");
        int t = bundle.getInt("tf");
        int n = bundle.getInt("num");

        total.setText("The total number of questions is: " + tot);
        mc.setText("There are " + m + " multiple choice questions");
        tf.setText("There are " + t + " true/false questions");
        num.setText("There are " + n + " numeric questions");

        back.setOnClickListener(e -> {
            finish();
        });


    }

}
