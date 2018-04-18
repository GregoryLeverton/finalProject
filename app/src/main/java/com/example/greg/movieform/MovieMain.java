package com.example.greg.movieform;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.greg.finalproject.R;


/**
 * Created by Tran on 2018-04-11.
 */

public class MovieMain extends Activity {

    Button addButton;
    Button listButton;
    Button uploadButton;
    Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity_main);

        this.addButton = findViewById(R.id.addMovieButton);
        this.listButton = findViewById(R.id.listMovieButton);
        this.helpButton = findViewById(R.id.aboutMovieButton);


        this.addButton.setOnClickListener((view) -> {
                    Intent aMovie = new Intent(MovieMain.this, com.example.greg.movieform.AddMovie.class);
                    startActivityForResult(aMovie, 100);

                }

        );

        this.listButton.setOnClickListener((view) -> {
                    Intent lMovie = new Intent(MovieMain.this, ListMovieActivity.class);
                    startActivity(lMovie);

                }

        );

        this.helpButton.setOnClickListener((view) -> {
                    Intent help = new Intent(MovieMain.this, MovieHelp.class);
                    startActivity(help);

                }

        );
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            Log.i("Movie man", "Returned to StartActivity.OnActivityResult");
        }
        if (resultCode == Activity.RESULT_OK) {

        }


    }

}
