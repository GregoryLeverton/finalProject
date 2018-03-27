package com.example.greg.octranspo.octranspo;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jason on 21/03/18.
 */

public class BusStopSearch  extends AsyncTask<Short, Integer, String> {

    public final static int SUCCESS = 0;
    public final static int ROUTE_NOT_FOUND = 1;
    public final static int XML_PARSER_ERROR = 2;
    public final static int IO_ERROR = 3;
    public final static int DOWNLOAD_ERROR = 4;

    protected final static String apiUrl = "http://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";

    private short stopId;
    private String stopName;

    private String queryUrl;

    private InputStream results;

    private int code;

    private ArrayList<Route> routes;

    @Override
    protected String doInBackground(Short... stopId)  {
        this.stopId = stopId[0];

        queryUrl = apiUrl + stopId[0].toString();

        Log.i("BusStopSearch", "Downloading xml data");
        Log.i("BusStopSearch", queryUrl);
        results = downloadUrl(queryUrl);

        BusStopXmlParser parser = new BusStopXmlParser(results);

        try {
            routes = parser.parse();
            stopName = parser.getStopName();
            code = SUCCESS;
        } catch(BusStopNotFoundException e) {
            code = ROUTE_NOT_FOUND;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            code = XML_PARSER_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            code = IO_ERROR;
        }

        return null;

    }

    public int getResultCode() {
        return code;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public String getStopName() {
        return stopName;
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
