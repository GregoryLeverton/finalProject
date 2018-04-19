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

import android.util.Log;
import android.view.View;

import android.widget.Button;

import android.widget.TextView;
import com.example.greg.finalproject.R;
import com.example.greg.finalproject.StartActivity;

import java.util.ArrayList;

public class PatientDetails extends Activity {

    String  name, birthday, age, gender, description,  phoneNumber, address, healthCardNumber;
    TextView nameText, birthdayText, ageText, genderText, descriptionText, phoneNumberText, addressText, healthCardNumberText;

    /**
     * On create function, set  content view and find name text
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        //Find view by ids
        nameText =(TextView)findViewById(R.id.entered_name);
        birthdayText=(TextView)findViewById(R.id.entered_birthday);
        ageText=(TextView)findViewById(R.id.entered_age);
        genderText=(TextView)findViewById(R.id.entered_gender);
        descriptionText=(TextView)findViewById(R.id.entered_description);
        phoneNumberText=(TextView)findViewById(R.id.entered_phoneNumber);
        addressText=(TextView)findViewById(R.id.entered_address);
        healthCardNumberText=(TextView)findViewById(R.id.entered_healthCardNumber);

        //Function to display the record of the patients name
        displayPatient();

        //When we click ok button take us back to start activity of the main page
        Button OK = (Button) findViewById(R.id.ok);
        OK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDetails.this, StartActivity.class);
                startActivity(intent);
                Log.i("PatientDetails", "Go back to main page");
            }
        });

        //On clicking delete button delete the name from the database and goes back to form page
        Button Del = (Button) findViewById(R.id.delete);
        Del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            finish();
                Log.i("PatientDetails", "Patient deleted");
            }

        });
    }

    //birthday, age,description,phoneNumber,healthCardNumber,gender;
    //Function to display the details of the patient
    private void displayPatient () {
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        nameText.setText(name);

        birthday = intent.getStringExtra("birthday");
        birthdayText.setText(birthday);

        age = intent.getStringExtra("age");
        ageText.setText(age);

        address = intent.getStringExtra("address");
        addressText.setText(address);

        description = intent.getStringExtra("description");
        descriptionText.setText(description);

        phoneNumber = intent.getStringExtra("phoneNumber");
        phoneNumberText.setText(phoneNumber);

        healthCardNumber = intent.getStringExtra("healthCardNumber");
        healthCardNumberText.setText(healthCardNumber);

        gender = intent.getStringExtra("gender");
        genderText.setText(gender);


    }
}
