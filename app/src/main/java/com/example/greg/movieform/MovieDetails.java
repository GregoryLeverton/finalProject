package com.example.greg.movieform;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greg.finalproject.R;

/**
 * Created by Tran on 2018-04-12.
 */

public class MovieDetails extends Fragment {

    int idKey;
    int position;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_add_movie, container, false);

        Bundle movieExtras = getArguments();

        idKey = movieExtras.getInt("id");
        position = movieExtras.getInt("position");


        return view;



    }
}
