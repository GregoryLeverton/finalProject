package com.example.greg.movieform;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.greg.finalproject.R;

public class MovieDetailsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle movieDetailsBundle = getIntent().getExtras();

        MovieFragment movieFragment = new MovieFragment();
        movieFragment.setArguments(movieDetailsBundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.phoneDetails, movieFragment).addToBackStack(null).commit();



    }
}
