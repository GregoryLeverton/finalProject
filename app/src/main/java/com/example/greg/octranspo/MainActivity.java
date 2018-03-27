package com.example.greg.octranspo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.greg.finalproject.R;

public class MainActivity extends Activity {


    private Button startAppBtn, aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.octranspo_activity_main);

        startAppBtn = (Button) findViewById(R.id.startAppBtn);
        aboutBtn = (Button) findViewById(R.id.aboutBtn);

        startAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startAppIntent = new Intent(MainActivity.this, SearchStopsActivity.class);
                startActivity(startAppIntent);
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the about page
            }
        });
    }
}