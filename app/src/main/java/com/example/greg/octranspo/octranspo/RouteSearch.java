package com.example.greg.octranspo.octranspo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.greg.octranspo.octranspo.BusStopSearch.*;

/**
 * Created by jason on 02/04/18.
 */

public class RouteSearch extends AsyncTask<String, Integer, String>{


    protected final static String apiUrl = "https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&format=json&";

    private short routeNum;
    private String direction;
    private short stopNum;

    private String queryUrl;

    private int code;


    private ArrayList<Trip> trips;

    @Override
    protected String doInBackground(String... params) {

        this.routeNum = Short.valueOf(params[0]);
        this.direction = params[1];
        this.stopNum = Short.valueOf(params[2]);

        //stopNo=3017&routeNo=95
        //95, Barrhaven Centre, 3000


        queryUrl = apiUrl + "stopNo=" + stopNum + "&routeNo=" + routeNum;

        Log.i("RouteSearch", queryUrl);

        String results = downloadUrl(queryUrl);

        RouteJsonParser parser = new RouteJsonParser(results, direction);

        trips = parser.getTripsList();

        return null;
    }

    private String downloadUrl(String urlString) {
        try {
            String result;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = DOWNLOAD_ERROR;
        }
        return null;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }
}
