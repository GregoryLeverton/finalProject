/**
 * This implements page to show the ptient name, and give option to delete and click ok to go back to start
 * @author [ Harleen Gakhar, ID#040888308]
 * @version 1.0
 * @see android.Project
 * @since 2018-04-17
 */
package com.example.greg.patientintake;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.TextView;
import com.example.greg.finalproject.R;
import com.example.greg.finalproject.StartActivity;

import java.util.ArrayList;

public class PatientDetails extends Activity {

    String  name;
    TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        nameText =(TextView)findViewById(R.id.name_entered);

        //Function to display the record of the patients name
        displayNameOfPatient();

        //When we click ok button take us back to start activity of the main page
        Button OK = (Button) findViewById(R.id.ok);
        OK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDetails.this, StartActivity.class);
                startActivity(intent);
            }
        });

        //On clicking delete button delete the name from the database and goes back to form page
        Button Del = (Button) findViewById(R.id.delete);
        Del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            finish();
            }

        });
    }

    //Function to display the name of the patient
    private void displayNameOfPatient () {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        nameText.setText(name);
    }
}
