package com.example.greg.octranspo.octranspo;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by jason on 02/04/18.
 */

public class RouteXmlParser {


    private XmlPullParser parser;

    private ArrayList<Trip> trips;

    public RouteXmlParser(InputStream input) {

        try {
            parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(input, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Route> parse() throws IOException, XmlPullParserException {

        trips = new ArrayList<Trip>();

        parser.nextTag();

        parser.require(XmlPullParser.START_TAG, null, "soap:Envelope");

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            int eventType = parser.getEventType();

            switch(eventType) {
                case XmlPullParser.START_TAG:
                    if(parser.getName().equals("Trips")){
                        return null;
                    }
            }
        }

        return null;

    }



    public Trip getNextTrip() {
        return null;
    }


}
