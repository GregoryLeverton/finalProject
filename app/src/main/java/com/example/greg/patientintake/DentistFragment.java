/**
 * This implements the fragment for dentist
 * @author [ Harleen Gakhar, ID#040888308]
 * @version 1.0
 * @see android.Project
 * @since 2018-04-17
 */
package com.example.greg.patientintake;

import android.os.Bundle;
import com.example.greg.finalproject.R;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class DentistFragment extends Fragment {

   RadioGroup Brace, Benefit;

   //Show Braces and medical benefits in the new fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.activity_dentist_fragment,
                container, false);
        Brace = (RadioGroup) view.findViewById(R.id.braces);
        Benefit = (RadioGroup) view.findViewById(R.id.medben);
        return view;
    }

}

