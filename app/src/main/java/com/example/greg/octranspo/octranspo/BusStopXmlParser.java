package com.example.greg.octranspo.octranspo;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by jason on 21/03/18.
 */

public class BusStopXmlParser {

    private XmlPullParser parser;

    private String stopName;

    public BusStopXmlParser(InputStream input) {
        try {
            parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(input, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getStopName() {
        return stopName;
    }

    public ArrayList<Route> parse() throws BusStopNotFoundException, IOException, XmlPullParserException {

        ArrayList<Route> routes = new ArrayList<>();

        Log.i("BusStopXmlParser", "Starting parser");
        parser.nextTag();

        parser.require(XmlPullParser.START_TAG, null, "soap:Envelope");

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            Log.i("ParserName", name);
            int eventType = parser.getEventType();

            if (name.equals("Error")) {
                parser.require(XmlPullParser.START_TAG, null, "Error");

                parser.next();

                if (parser.getEventType() == XmlPullParser.TEXT) {
                    if (parser.getText().equals("10")) {
                        Log.e("Parser", "BusStopNotFound");
                        throw new BusStopNotFoundException();
                    }
                }
                stopName = parser.getText();
            }

            if (name.equals("StopDescription")) {
                parser.require(XmlPullParser.START_TAG, null, "StopDescription");

                parser.next();
                stopName = parser.getText();

                Log.i("StopDescription", "Stop Name: " + stopName);

                parser.next();
            }

            if (name.equals("Routes")) {
                parser.require(XmlPullParser.START_TAG, null, "Routes");
                parser.nextTag();

                //Log.i("After routes", "name: " + parser.getName());
            }

            if (parser.getName().equals("Route")) {
                parser.require(XmlPullParser.START_TAG, null, "Route");
                parser.next();
            }

            if (parser.getName().equals("RouteNo")) {
                String routeHeading = " - ";
                String routeNumber;

                parser.next();
                //Log.i("RouteNo", "" + parser.getText());
                routeNumber = parser.getText();
                String nextName = "";
                while (!nextName.equals("RouteHeading")) {
                    parser.next();
                    if (parser.getName() != null)
                        nextName = parser.getName();
                }
                if (nextName.equals("RouteHeading")) {
                    parser.require(XmlPullParser.START_TAG, null, "RouteHeading");
                    parser.next();
                    if (parser.getEventType() == XmlPullParser.TEXT) {
                        routeHeading = parser.getText();
                    }
                }
                Route newRoute = new Route(Short.parseShort(routeNumber), routeHeading);
                routes.add(newRoute);
                Log.i("Route at Stop", newRoute.getRouteNumber() + ": " + newRoute.getRouteHeading());
            }


            eventType = parser.next();

            parser.next();

        }

        return routes;
    }


}
