package com.example.greg.movieform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.greg.finalproject.R;
import java.util.ArrayList;
import android.support.v4.app.FragmentActivity;





public class ListMovieActivity extends FragmentActivity {

    ListView movieList;
    protected Cursor c;
    Boolean hasFrameLayout;
    MovieAdapter movieAdapter;
    ArrayList<Movie> movies = new ArrayList<>();
    String title;
    String actor;
    String length;
    String desc;
    int rating;
    String url;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ListMovie", "In onCreate()");
        setContentView(R.layout.movie_list);


        this.movieList = findViewById(R.id.movieList);


        hasFrameLayout = (findViewById(R.id.rightPanel) != null) ? true : false;

        //created a getMoviesFromDB utility method to handle database query
        this.movies = this.getMoviesFromDB();

        this.movieAdapter = new MovieAdapter(this, this.movies);

        this.movieList.setAdapter(movieAdapter);



        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("In setOnItemClick", "onItemClick: ");

                Movie movie = movies.get(i);

                Bundle bundle = new Bundle();
                bundle.putString("title", movie.getTitle());
                bundle.putString("actors", movie.getActors());
                bundle.putString("length", movie.getLength());
                bundle.putString("desc", movie.getDesc());
                bundle.putInt("rating", movie.getRating());
                bundle.putString("url", movie.getUrl());
                bundle.putInt("id",movie.getId());
                bundle.putBoolean("hasFrameLayout",hasFrameLayout);
                bundle.putInt("listPosition", i);


                //wide
                if (hasFrameLayout==true){

                    MovieFragment movFragment = new MovieFragment();
                    movFragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.rightPanel, movFragment).addToBackStack(null).commit();

                }

                else{
                    Intent intent = new Intent(ListMovieActivity.this, MovieDetailsActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 100);

                }
            }

        });
    }

    public ArrayList<Movie> getMoviesFromDB(){

        MovieDatabaseHelper movieHelper = new MovieDatabaseHelper(this);
        SQLiteDatabase db = movieHelper.getWritableDatabase();
        ArrayList<Movie> movies = new ArrayList<>();

        c = db.rawQuery("SELECT * FROM " + MovieDatabaseHelper.TABLE_NAME + ";", null);
        c.moveToFirst();

        while (!c.isAfterLast()) {

            title = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_TITLE));
            actor = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_ACTORS));
            length = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_LENGTH));
            desc = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_DESC));
            rating = c.getInt(c.getColumnIndex(MovieDatabaseHelper.MOVIE_RATING));
            url = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_URL));
            id = c.getInt(c.getColumnIndex(MovieDatabaseHelper.KEY_ID));

            Movie movie = new Movie(id, title, actor, length, desc, rating, url);

            Log.i("ListMovies", "message retrieved from Cursor");
            movies.add(movie);
            c.moveToNext();


        }

        return movies;


    };

    @Override
    public void onResume() {
        super.onResume();



    }

    public void deleteMovie(int position){
        this.movies.remove(position);

    }


    private class MovieAdapter extends ArrayAdapter<Movie> {


        public MovieAdapter(Context ctx, ArrayList<Movie> movies) {
            super(ctx, 0, movies);

        }

        public View getView(int position, View convertView, ViewGroup parent) {

            Movie movie = this.getItem(position);


            if (movie != null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_listlayout, parent, false);

                TextView titleIn = convertView.findViewById(R.id.movieTitle);
                titleIn.setText(movie.getTitle());
                TextView ratingIn = convertView.findViewById(R.id.movieRating);
                ratingIn.setText(String.valueOf(movie.getRating()));
                ImageView posterView = convertView.findViewById(R.id.poster);

                //created a new asyncTask so it can be used by other classes (eg. MovieFragment)
                DownloadImageAsyncTask getImage = new DownloadImageAsyncTask(posterView,movie.getUrl());
                getImage.execute();

            }
            return convertView;


        }




    }

    //this method is called when phone is in landscape
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //400 refers to the result returned from Movie update;
        if (requestCode == 400 && resultCode == RESULT_OK) {

            //reference the movieFragment to update with new values from UpdateMovieActivity
            MovieFragment movieFragment = (MovieFragment)getSupportFragmentManager().getFragments().get(0);

            //retrieve attributes passed back from Fragment that contains updated movie details
            Movie movie = MovieDatabaseHelper.getMovieFromDB(getBaseContext(),data.getExtras().getInt("id"));

            movieFragment.getTitleView().setText(movie.getTitle());
            movieFragment.getActorsView().setText(movie.getActors());
            movieFragment.getLengthView().setText(movie.getLength());
            movieFragment.getDescView().setText(movie.getDesc());
            movieFragment.getRatingView().setText(String.valueOf(movie.getRating()));


            DownloadImageAsyncTask imgAsyncTask = new DownloadImageAsyncTask(movieFragment.getPosterView(),movie.getUrl());
            imgAsyncTask.execute();

            this.reloadAdapterListFromDB();

            //this.movies.add(new Movie(23,"asdf","asdf","asdf","asdf",23,"asdf"));
            movieAdapter.notifyDataSetChanged();


            
            // ;
        }


    }

    private void reloadAdapterListFromDB(){
        this.movies.clear();
        this.movies.addAll(getMoviesFromDB());

    }




}





 /*       Snackbar.make(findViewById(android.R.id.content), "Had a snack at Snackbar", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
        button1.setOnClickListener(e ->{
                    CharSequence text = "you pushed a button";// "Switch is Off"
                    int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off

                    Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity

                    toast.show();
                }
        );

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Place Holder")
                .setTitle("Place Holder")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


        }*/





