package com.example.greg.quizgame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.example.greg.finalproject.R;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Button button1 = (Button)findViewById(R.id.quiz_button);



        

        Snackbar.make(findViewById(android.R.id.content), "Had a snack at Snackbar", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
        button1.setOnClickListener(e ->{
            CharSequence text = "you pushed a button";// "Switch is Off"
            int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off

            Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity

            toast.show();
                            }



        );

    }
}