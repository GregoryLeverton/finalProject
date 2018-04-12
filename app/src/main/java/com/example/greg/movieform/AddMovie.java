package com.example.greg.movieform;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.greg.finalproject.R;

/**
 * Created by Tran on 2018-04-11.
 */

public class AddMovie extends Activity{

    Button download;
     Button addMovieButton;
     EditText title;
     EditText actors;
     EditText length;
     EditText desc;
     EditText rating;
     EditText posterUrl;
     ProgressBar progressBar;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_add_movie);

        this.addMovieButton = findViewById(R.id.buttonAdd);
        this.download = findViewById(R.id.download);

        this.addMovieButton.setOnClickListener((view) -> {




        });

        this.download.setOnClickListener((view) -> {




        });




    }
}