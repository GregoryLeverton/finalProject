package com.example.greg.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button buttonGreg = (Button) findViewById(R.id.greg);
        Button buttonHarleen = (Button) findViewById(R.id.harleen);
        Button buttonTran = (Button) findViewById(R.id.tran);
        Button buttonJason = (Button) findViewById(R.id.jason);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        buttonJason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchJasonActivity = new Intent(StartActivity.this, com.example.greg.octranspo.MainActivity.class);
                startActivity(launchJasonActivity);
            }
        });

        buttonGreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchGregActivity = new Intent(StartActivity.this, com.example.greg.quizgame.QuizActivity.class);
                startActivity(launchGregActivity);
            }
        });

        buttonHarleen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchHarleenActivity = new Intent(StartActivity.this, com.example.greg.patientintake.PatientActivity.class);
                startActivity(launchHarleenActivity);
            }
        });

        buttonTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchTranActivity = new Intent(StartActivity.this, com.example.greg.movieform.MovieActivity.class);
                startActivity(launchTranActivity);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem mi){

        switch(mi.getItemId()){
            case R.id.action_quiz:
                Log.d("Toolbar", "Option 1 selected");

                Intent launchGregActivity = new Intent(StartActivity.this, com.example.greg.quizgame.QuizActivity.class);
                startActivity(launchGregActivity);
                break;
            case  R.id.action_patient:
                Intent launchHarleenActivity = new Intent(StartActivity.this, com.example.greg.patientintake.PatientActivity.class);
                startActivity(launchHarleenActivity);
                break;

            case R.id.action_movie:
                Intent launchTranActivity = new Intent(StartActivity.this, com.example.greg.movieform.MovieActivity.class);
                startActivity(launchTranActivity);

                break;

            case R.id.action_oc:
                Intent launchJasonActivity = new Intent(StartActivity.this, com.example.greg.octranspo.MainActivity.class);
                startActivity(launchJasonActivity);
                break;

        }
        return true;
    }
}
