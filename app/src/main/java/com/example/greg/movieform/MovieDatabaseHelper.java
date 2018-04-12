package com.example.greg.movieform;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static java.sql.Types.VARCHAR;

/**
 * Created by Tran on 2018-04-11.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "movies.db";
    public static final int VERSION_NUM = 0;
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
        db.execSQL("CREATE TABLE "+TABLE_NAME+"("+KEY_ID+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                ""+MOVIE_TITLE+" TEXT,"+MOVIE_ACTORS+" TEXT,"+MOVIE_LENGTH+" INT, " +
                ""+MOVIE_DESC+" TEXT,"+MOVIE_RATING+" INT, "+MOVIE_URL+" VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("ChatDatabaseHelper", "Calling OnUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        Log.i("ChatDatabaseHelper", "Calling onDowngrade");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }
    public void onOpen(SQLiteDatabase db){
        Log.i("ChatDatabaseHelper","DATABASE opened");
    }

}