package com.example.greg.movieform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tran on 2018-04-17.
 */
public class DownloadImageAsyncTask extends AsyncTask<String, Integer, String> {

    ImageView poster;
    Bitmap posterImage;
    String url;

    public DownloadImageAsyncTask(ImageView poster,String url){
        this.poster = poster;
        this.url = url;
    }
    @Override
    protected String doInBackground(String... strings) {

        this.posterImage =getImage(this.url);

        return "";

    }

    @Override
    protected void onPostExecute(String result) {
        if (this.posterImage != null){
            this.poster.setImageBitmap(this.posterImage);
        }


    }

    public Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public Bitmap getImage(String urlString) {
        try {
            URL url = new URL(urlString);
            return getImage(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}

