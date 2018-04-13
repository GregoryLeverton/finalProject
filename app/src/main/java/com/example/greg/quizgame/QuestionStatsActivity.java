package com.example.greg.quizgame;
/**
 * Created by Greg Leverton on 2018-04-10.
 */
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
        TextView total = findViewById(R.id.total);
        TextView mc = findViewById(R.id.MC);
        TextView tf =  findViewById(R.id.TF);
        TextView num = findViewById(R.id.Num);
        Button back = findViewById(R.id.back);

        Bundle bundle = getIntent().getBundleExtra("StatsItem");
        int tot = bundle.getInt("tot");
        int m = bundle.getInt("mc");
        int t = bundle.getInt("tf");
        int n = bundle.getInt("num");

        total.setText(getString(R.string.totalQues)+" " + tot);
        mc.setText(getString(R.string.thereAre)+" " + m +" "+ getString(R.string.mcq));
        tf.setText(getString(R.string.thereAre)+" " + t +" "+ getString(R.string.tfq));
        num.setText(getString(R.string.thereAre)+" " + n +" "+ getString(R.string.numq));

        back.setOnClickListener(e -> {
            finish();
        });


    }

}
