/**
 * This implements main page with the form
 * Includes action buttons to save and cancel the patient
 * Input the values in the database
 * Include option to open the new fragment of dentist, doctor and optometrist
 * @author [ Harleen Gakhar, ID#040888308]
 * @version 1.0
 * @see android.Project
 * @since 2018-04-17
 */

package com.example.greg.patientintake;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.example.greg.finalproject.R;
import static com.example.greg.patientintake.PatientDatabaseHelper.Address;
import static com.example.greg.patientintake.PatientDatabaseHelper.Age;
import static com.example.greg.patientintake.PatientDatabaseHelper.DOB;
import static com.example.greg.patientintake.PatientDatabaseHelper.Reason;
import static com.example.greg.patientintake.PatientDatabaseHelper.Gender;
import static com.example.greg.patientintake.PatientDatabaseHelper.Card;
import static com.example.greg.patientintake.PatientDatabaseHelper.Name;
import static com.example.greg.patientintake.PatientDatabaseHelper.Phone;

public class PatientMain extends Activity implements AdapterView.OnItemSelectedListener {

    String  name, address, age, birthday, phoneNumber, healthCardNumber, gender, description;
    String[] dataToFragment;
    EditText nameText, addressText, ageText, birthdayText, phoneNumberText, healthCardNumberText, descriptionText;
    RadioButton maleRadioBtn, femaleRadioBtn;
    Spinner patientTypeText;
    RadioGroup radioGender;

    /**
     * On create method, set content view (xml), get ids from xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main);

        Button saveBtn = (Button) findViewById(R.id.save);
        Button cancelBtn = (Button) findViewById(R.id.cancel);
        nameText = (EditText) findViewById(R.id.name);
        addressText = (EditText) findViewById(R.id.address);
        ageText = (EditText) findViewById(R.id.age);
        birthdayText = (EditText) findViewById(R.id.birthday);
        radioGender = (RadioGroup) findViewById(R.id.radioSex);
        maleRadioBtn = (RadioButton) findViewById(R.id.male);
        femaleRadioBtn = (RadioButton) findViewById(R.id.female);
        phoneNumberText = (EditText) findViewById(R.id.phone_number);
        healthCardNumberText = (EditText) findViewById(R.id.health_card_number);
        descriptionText = (EditText) findViewById(R.id.description);
        patientTypeText = (Spinner) findViewById(R.id.patient_type);


    /**
    *Reference
    * https://stackoverflow.com/questions/16581536/setonitemselectedlistener-of-spinner-does-not-call
    */
        // Spinner click listener
        patientTypeText.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> type = new ArrayList<String>();
        type.add("Select Patient Type");
        type.add("Doctor");
        type.add("Dentist");
        type.add("Optometrist");

        //Create adapter for spinner and drop down list
        /**
         * Reference: https://stackoverflow.com/questions/6485158/custom-style-setdropdownviewresource-android-spinner
         */
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientTypeText.setAdapter(dataAdapter);


        //On clicking save button save all the data that is being entered
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                address = addressText.getText().toString();
                age = ageText.getText().toString();
                birthday = birthdayText.getText().toString();
                int selectedId =radioGender.getCheckedRadioButtonId();
                RadioButton radioSexButton = (RadioButton)findViewById(selectedId);
                gender = radioSexButton.getText().toString();
                phoneNumber = phoneNumberText.getText().toString();
                healthCardNumber = healthCardNumberText.getText().toString();
                description = descriptionText.getText().toString();

                //make name an mandatory entry
                if (name.isEmpty()){
                    nameText.setError("Name field is manadtory");
                    nameText.requestFocus();
                }
                else {

                    /**
                     * Call database helper and put the values in database using Contenet values
                     * Creates an empty set of values using the default initial size . Put Adds values to the set.
                     * Ref: https://stackoverflow.com/questions/15384550/android-sqlite-contentvalues-not-inserting
                     */
                    PatientDatabaseHelper myDbHelper = new PatientDatabaseHelper(com.example.greg.patientintake.PatientMain.this);
                    SQLiteDatabase writableDb = myDbHelper.getWritableDatabase();
                    SQLiteDatabase readableDb = myDbHelper.getReadableDatabase();
                    ContentValues cValues = new ContentValues();
                    cValues.put(Name, name);
                    cValues.put(Address, address);
                    cValues.put(Age, age);
                    cValues.put(DOB, birthday);
                    cValues.put(Gender, gender);
                    cValues.put(Phone, phoneNumber);
                    cValues.put(Card, healthCardNumber);
                    cValues.put(Reason, description);

                    /**
                     * Reference: https://stackoverflow.com/questions/5265913/how-to-use-putextra-and-getextra-for-string-data
                     *  Takes you to move from one activity to another activity,we have to use method putExtra();
                     *  pass your values and retrieve them in the other Activity using AnyKeyName
                     *  intent.putExtra("AnyKeyName", getText());
                     */
                    Intent intent = new Intent(com.example.greg.patientintake.PatientMain.this, PatientDetails.class);
                    intent.putExtra("name", name);
                    intent.putExtra("address", address);
                    intent.putExtra("age", age);
                    intent.putExtra("birthday", birthday);
                    intent.putExtra("phoneNumber", phoneNumber);
                    intent.putExtra("healthCardNumber", healthCardNumber);
                    intent.putExtra("gender", gender);
                    intent.putExtra("description", description);

                    Log.i("PatientMain", "Hello Harleen");

                    startActivity(intent);
                }
            }
        });

        //On clicking the cancel button finish the process
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


    /**Fragment manager to open the new fragments for Dentist, doctor and Optometrist
     * Create toast message on slecting the three options
     * Reference: https://developer.android.com/reference/android/widget/AdapterView.OnItemSelectedListener.html
     * @param parent: AdapterView: The AdapterView where the selection happened
     * @param view: View: The view within the AdapterView that was clicked
     * @param position: int: The position of the view in the adapter
     * @param id: long: The row id of the item that is selected
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (selection.toLowerCase()) {
            //Call Doctor fragment
            case "doctor":
                ft.replace(R.id.FrameLayout, new DoctorFragment());
                ft.commit();
                Toast.makeText(parent.getContext(), "Type of patient to input: " + selection, Toast.LENGTH_LONG).show();
                break;
             //Call dentist fragment
            case "dentist":
                ft.replace(R.id.FrameLayout, new DentistFragment());
                ft.commit();
                Toast.makeText(parent.getContext(), "Type of patient to input: " + selection, Toast.LENGTH_LONG).show();
                break;
            //Call optometrist fragment
            case "optometrist":
                ft.replace(R.id.FrameLayout, new OptometristFragment());
                ft.commit();
                Toast.makeText(parent.getContext(), "Type of patient to input: " + selection, Toast.LENGTH_LONG).show();
                break;
            default: break;
        }
    }



    /**
     *Reference
     * https://stackoverflow.com/questions/16581536/setonitemselectedlistener-of-spinner-does-not-call
     */
    public void onNothingSelected(AdapterView<?> parent) {

    }
}