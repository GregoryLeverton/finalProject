/**
 * This implements the database for the paient intake form
 * @author [ Harleen Gakhar, ID#040888308]
 * @version 1.0
 * @see android.Project
 * @since 2018-04-17
 */
package com.example.greg.patientintake;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

public class PatientDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Patient.db";
    public static final int VERSION_NUM = 2;
    public static final String KEY_ID = "id";
    public static final String TB_NAME = "TN";
    public static final String Type="Type";
    public static final String Name="Name";
    public static final String Address="Address";
    public static final String Age="Age";
    public static final String DOB="DOB";
    public static final String Gender="Gender";
    public static final String Phone="Phone";
    public static final String Card="Card";
    public static final String Reason="Reason";

    public PatientDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    //Create the database, execute query
    @Override
    public void onCreate(SQLiteDatabase db){
        String create_table_query = " CREATE TABLE "+TB_NAME+"("+ KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + Type+" TEXT,"+ Name+" Text,"+ Address+" TEXT,"+ Age+" TEXT,"+ DOB+" TEXT,"+ Gender+" TEXT,"+ Phone+" TEXT,"+ Card+" TEXT,"+ Reason+" TEXT);";
        db.execSQL(create_table_query);
        }

    //Write query to upgrade database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("PatientDatabaseHelper", "Calling onUpgrade, old Version=" + oldVer + " newVersion=" + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }
}