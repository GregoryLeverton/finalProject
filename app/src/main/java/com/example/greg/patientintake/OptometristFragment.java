/**
 * This implements the fragment for optometrist fragment
 * @author [ Harleen Gakhar, ID#040888308]
 * @version 1.0
 * @see android.Project
 * @since 2018-04-17
 */
package com.example.greg.patientintake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.greg.finalproject.R;
import android.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.EditText;

public class OptometristFragment extends Fragment {

    EditText Date, Store;

    /**Create the view for this fragment, using the arguments given to it.Optometrist fragment  to display date and store where glasses purchased
     * Ref: https://developer.android.com/reference/android/app/Fragment.html
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_optometrist_fragment,
                container, false);
        Date = (EditText) view.findViewById(R.id.date);
        Store = (EditText) view.findViewById(R.id.store);
        return view;
    }
}

