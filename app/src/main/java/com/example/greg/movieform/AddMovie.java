package com.example.greg.movieform;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.greg.finalproject.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Tran on 2018-04-11.
 */

public class AddMovie extends Activity {

    Button download;
    Button addMovieButton;
    EditText title;
    EditText actors;
    EditText length;
    EditText desc;
    EditText rating;
    EditText posterUrl;
    ProgressBar progressBar;
    SQLiteDatabase db;
    MovieDatabaseHelper movieHelper;
    AddMovie addmovie;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addmovie = this;
        setContentView(R.layout.movie_add_movie);

        this.title = findViewById(R.id.titleinput);
        this.actors = findViewById(R.id.actorinput);
        this.length = findViewById(R.id.lengthinput);
        this.desc = findViewById(R.id.descinput);
        this.rating = findViewById(R.id.ratinginput);
        this.posterUrl = findViewById(R.id.urlinput);


        this.addMovieButton = findViewById(R.id.buttonAdd);
        this.download = findViewById(R.id.downloadbutton);


        this.addMovieButton.setOnClickListener((view) -> {
            Log.i("AddMovie", "MovieHelper opened db");

            movieHelper = new MovieDatabaseHelper(this);
            db = movieHelper.getWritableDatabase();

            ContentValues movieContent = new ContentValues();

            movieContent.put(MovieDatabaseHelper.MOVIE_TITLE, title.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_ACTORS, actors.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_LENGTH, length.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_DESC, desc.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_RATING, rating.getText().toString());
            movieContent.put(MovieDatabaseHelper.MOVIE_URL, posterUrl.getText().toString());

            db.insert(MovieDatabaseHelper.TABLE_NAME, "added new movie", movieContent);

            setResult(Activity.RESULT_OK);
            finish();

        });

        download.setOnClickListener((view) -> {

            Download xml = new Download();
            xml.execute();

        });

    }



    public class Download extends AsyncTask<String, Integer, String> {

        ArrayList <Movie> movies = new ArrayList<>();


        @Override
        protected String doInBackground(String... strings) {


            try {
                Log.i("Async", "I'm in do in background");
                URL url = new URL(" http://torunski.ca/CST2335/MovieInfo.xml");


                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();

                InputStream iStream = urlConnection.getInputStream();

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(iStream);

                Element element = doc.getDocumentElement();
                element.normalize();

                NodeList nList = doc.getElementsByTagName("Movie");
                Log.i("Async", "Node: " + nList.getLength());
                Log.i("Async", "Node: " + nList.toString());
                Log.i("Async", "iStream: " + iStream.toString());

                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    Log.i("Async", "Node: " + nList.item(i));
                    NodeList movieDetailNodes = node.getChildNodes();
                    Movie movie = new Movie();
                    Log.i("Async", "Node List: " + node.getNodeName());
                    ContentValues moviexml = new ContentValues();

                    for (int j = 0; j < movieDetailNodes.getLength(); j++) {

                        Node detailNode = movieDetailNodes.item(j);
                        String detailName = detailNode.getNodeName();
                        String detailValue = detailNode.getTextContent();
                        Log.i("Async", "Node Details: " + detailName);

                        Log.i("Async", "detail name: "+detailName+"detail value: "+detailValue);

                        switch (detailName) {
                            case "Title":
                                moviexml.put(MovieDatabaseHelper.MOVIE_TITLE,detailValue);
                                break;

                            case "Actors":
                                moviexml.put(MovieDatabaseHelper.MOVIE_ACTORS, detailValue);
                                break;

                            case "Length":
                                moviexml.put(MovieDatabaseHelper.MOVIE_LENGTH, detailValue);
                                break;

                            case "Description":
                                moviexml.put(MovieDatabaseHelper.MOVIE_DESC,detailValue);
                                break;

                            case "Rating":

                                moviexml.put(MovieDatabaseHelper.MOVIE_RATING,detailValue);
                                break;

                            case "URL":
                               detailValue = ((Element) detailNode).getAttribute("value");

                                moviexml.put(MovieDatabaseHelper.MOVIE_URL,detailValue);
                                break;


                        }

                    }
                    MovieDatabaseHelper mh = new MovieDatabaseHelper(getBaseContext());
                    SQLiteDatabase dBase = mh.getWritableDatabase();

                    dBase.insert(MovieDatabaseHelper.TABLE_NAME," added XML movies", moviexml);
                    Log.i("Async", "added movie");
                    //db.insert(MovieDatabaseHelper.TABLE_NAME, "added new movie", movieContent);




                }

                setResult(Activity.RESULT_OK);
                finish();


            } catch (Exception e) {
                Log.e("Error", e.getStackTrace().toString());
                Log.e("break", Log.getStackTraceString(new NullPointerException()));


            }
            return null;
        }
    }

}