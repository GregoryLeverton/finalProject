package com.example.greg.patientintake;
/**
 * This implements the fragment for doctor
 * @author [ Harleen Gakhar, ID#040888308]
 * @version 1.0
 * @see android.Project
 * @since 2018-04-17
 */
import android.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.EditText;

import com.example.greg.finalproject.R;

public class DoctorFragment extends Fragment {
    EditText surgery;
    EditText allergy;

    //Show Surgeries and allergies in the new fragment  when we select doctor
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_doctor_fragment, container, false);
        surgery = (EditText) view.findViewById(R.id.surgeries);
        allergy = (EditText) view.findViewById(R.id.allergies);
        return view;
    }


}