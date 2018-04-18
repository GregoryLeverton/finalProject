package com.example.greg.movieform;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.greg.finalproject.R;

/**
 * Created by Tran on 2018-04-17.
 */

public class UpdateMovieActivity extends Activity {
    Button deleteButton;
    Button updateMovieButton;
    EditText title;
    EditText actors;
    EditText length;
    EditText desc;
    EditText rating;
    EditText posterUrl;

    Bundle movieBundle;

    int movieID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //we're going to reuse the movie_add_movie layout
        setContentView(R.layout.movie_add_movie);
        this.movieBundle = getIntent().getExtras();
        //get movie id passed in from previous Activity/Fragment (eg. MovieFragment)
        this.movieID = this.movieBundle.getInt("id");

        this.title = findViewById(R.id.titleinput);
        this.actors = findViewById(R.id.actorinput);
        this.length = findViewById(R.id.lengthinput);
        this.desc = findViewById(R.id.descinput);
        this.rating = findViewById(R.id.ratinginput);
        this.posterUrl = findViewById(R.id.urlinput);

        this.updateMovieButton = findViewById(R.id.buttonAdd);

        //change add movie button label to 'update'
        this.updateMovieButton.setText("UPDATE");
        this.deleteButton = findViewById(R.id.downloadbutton);
        //we're going to hide the download button because it's not applicable for the upload
        //and xml blurb
        this.deleteButton.setText("DELETE");
        findViewById(R.id.xmltext).setVisibility(View.GONE);


        Movie movie = MovieDatabaseHelper.getMovieFromDB(getBaseContext(),this.movieID);

        //set movie fields in update form
        this.title.setText(movie.getTitle());
        this.actors.setText(movie.getActors());
        this.length.setText(movie.getLength());
        this.desc.setText(movie.getDesc());
        this.rating.setText(String.valueOf(movie.getRating()));
        this.posterUrl.setText(movie.getUrl());


        this.deleteButton.setOnClickListener((view) -> {
            MovieDatabaseHelper movieHelper = new MovieDatabaseHelper(this);
            SQLiteDatabase db = movieHelper.getWritableDatabase();
            db.delete(movieHelper.TABLE_NAME,  movieHelper.KEY_ID +"="+String.valueOf(movieBundle.getInt("id")),null);
            Intent intent = new Intent(this, ListMovieActivity.class);
            startActivity(intent);

        });

        this.updateMovieButton.setOnClickListener((view) -> {
            Log.i("AddMovie", "MovieHelper opened db");



            ContentValues movieContent = new ContentValues();

            movieContent.put(MovieDatabaseHelper.MOVIE_TITLE, title.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_ACTORS, actors.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_LENGTH, length.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_DESC, desc.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_RATING, rating.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_URL, posterUrl.getText().toString());

            MovieDatabaseHelper movieHelper = new MovieDatabaseHelper(this);
            SQLiteDatabase db = movieHelper.getWritableDatabase();
            db.update(movieHelper.TABLE_NAME, movieContent, movieHelper.KEY_ID +"="+String.valueOf(movieBundle.getInt("id")),null);


            Intent intent = new Intent();

            intent.putExtra("id",this.movieBundle.getInt("id"));

            setResult(Activity.RESULT_OK, intent);
            finish();

        });



    }

}
