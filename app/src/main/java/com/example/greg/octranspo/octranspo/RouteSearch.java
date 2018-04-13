package com.example.greg.octranspo.octranspo;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.greg.octranspo.octranspo.BusStopSearch.*;

/**
 * Created by jason on 02/04/18.
 */

public class RouteSearch extends AsyncTask<String, Integer, String>{


    protected final static String apiUrl = "view-source:https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&";

    private short routeNum;
    private String direction;
    private short stopNum;

    private String queryUrl;

    private int code;


    private Trip nextTrip;

    @Override
    protected String doInBackground(String... params) {

        this.routeNum = Short.valueOf(params[0]);
        this.direction = params[1];
        this.stopNum = Short.valueOf(params[2]);

        //stopNo=3017&routeNo=95


        queryUrl = apiUrl + "stopNo=" + stopNum + "&routeNo=" + routeNum;

        InputStream results = downloadUrl(queryUrl);

        RouteXmlParser parser = new RouteXmlParser(results);

        nextTrip = parser.getNextTrip();


        return null;
    }

    private InputStream downloadUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            return conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            code = DOWNLOAD_ERROR;
        }
        return null;
    }
}
