package com.example.greg.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button buttonGreg = (Button)findViewById(R.id.greg);
        Button buttonHarleen = (Button)findViewById(R.id.harleen);
        Button buttonTran = (Button)findViewById(R.id.tran);
        Button buttonJason = (Button)findViewById(R.id.jason);
    }
}
