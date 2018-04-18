package com.example.greg.movieform;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static java.sql.Types.VARCHAR;

/**
 * Created by Tran on 2018-04-11.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "movies.db";
    public static final int VERSION_NUM = 5;
    public static final String KEY_ID="id";
    public static final String MOVIE_TITLE= "movieTitle";
    public static final String MOVIE_ACTORS ="movieActors";
    public static final String MOVIE_LENGTH ="movieLength";
    public static final String MOVIE_DESC = "movieDesc";
    public static final String MOVIE_RATING = "movieRating";
    public static final String TABLE_NAME="movieTable";
    public static final String MOVIE_URL="movieURL";

    public MovieDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_NAME+"("+KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ""+MOVIE_TITLE+" TEXT,"+MOVIE_ACTORS+" TEXT,"+MOVIE_LENGTH+" TEXT, " +
                ""+MOVIE_DESC+" TEXT,"+MOVIE_RATING+" INT, "+MOVIE_URL+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("MovieDatabaseHelper", "Calling OnUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        Log.i("MovieDatabaseHelper", "Calling onDowngrade");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }
    public void onOpen(SQLiteDatabase db){
        Log.i("MovieDatabaseHelper","DATABASE opened");
    }


    public ArrayList<Movie> getMoviesFromDB(Context ctx){

        MovieDatabaseHelper movieHelper = new MovieDatabaseHelper(ctx);
        SQLiteDatabase db = movieHelper.getWritableDatabase();
        ArrayList<Movie> movies = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + MovieDatabaseHelper.TABLE_NAME + ";", null);
        c.moveToFirst();

        while (!c.isAfterLast()) {

            String title = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_TITLE));
            String actor = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_ACTORS));
            String length = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_LENGTH));
            String desc = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_DESC));
            int rating = c.getInt(c.getColumnIndex(MovieDatabaseHelper.MOVIE_RATING));
            String url = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_URL));
            int id = c.getInt(c.getColumnIndex(MovieDatabaseHelper.KEY_ID));

            Movie movie = new Movie(id, title, actor, length, desc, rating, url);

            Log.i("ListMovies", "message retrieved from Cursor");
            movies.add(movie);
            c.moveToNext();


        }

        return movies;


    };

    //returns a movie from db with the key
    static public Movie getMovieFromDB(Context ctx, int key){
        MovieDatabaseHelper movieHelper = new MovieDatabaseHelper(ctx);
        SQLiteDatabase db = movieHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + MovieDatabaseHelper.TABLE_NAME + " WHERE "+MovieDatabaseHelper.KEY_ID+"= "+key+";", null);

        c.moveToFirst();
        String title = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_TITLE));
        String actor = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_ACTORS));
        String length = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_LENGTH));
        String desc = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_DESC));
        int rating = c.getInt(c.getColumnIndex(MovieDatabaseHelper.MOVIE_RATING));
        String url = c.getString(c.getColumnIndex(MovieDatabaseHelper.MOVIE_URL));
        int id = c.getInt(c.getColumnIndex(MovieDatabaseHelper.KEY_ID));

        Movie movie = new Movie(id, title, actor, length, desc, rating, url);

        return movie;


    }


}