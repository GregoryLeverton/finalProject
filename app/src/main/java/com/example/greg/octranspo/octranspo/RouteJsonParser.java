package com.example.greg.octranspo.octranspo;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jason on 02/04/18.
 */

public class RouteJsonParser {

    private ArrayList<Trip> tripsList;

    public RouteJsonParser(String input, String routeLabel) {

        tripsList = new ArrayList<Trip>();

        try {


            JSONArray routeDirections = new JSONObject(input)
                    .getJSONObject("GetNextTripsForStopResult")
                    .getJSONObject("Route")
                    .getJSONArray("RouteDirection");

            JSONObject targetRoute = null;

            for (int i=0; i < routeDirections.length(); i++)
            {
                JSONObject route = routeDirections.getJSONObject(i);
                    // Pulling items from the array
                Log.i("RouteLabel", route.getString("RouteLabel"));
                if(route.getString("RouteLabel").equals(routeLabel)) {
                    targetRoute = route;
                }
                //String oneObjectsItem = oneObject.getString("STRINGNAMEinTHEarray");
                //String oneObjectsItem2 = oneObject.getString("anotherSTRINGNAMEINtheARRAY");

            }

            JSONObject tripsObj = targetRoute.getJSONObject("Trips");

            //        .getJSONArray("Trip");

            JSONArray tripsArray = null;

            if(tripsObj.get("Trip") instanceof JSONArray) {
                tripsArray = tripsObj.getJSONArray("Trip");


                for (int i = 0; i < tripsArray.length(); i++) {
                    JSONObject tripData = tripsArray.getJSONObject(i);
                    Trip trip = new Trip();
                    // Pulling items from the array
                    trip.setTripDestination(tripData.getString("TripDestination"));
                    trip.setStartTime(tripData.getString("TripStartTime"));
                    trip.setLastTrip(tripData.getBoolean("LastTripOfSchedule"));
                    trip.setGpsLat(tripData.getString("Latitude"));
                    trip.setGpsLong(tripData.getString("Longitude"));
                    trip.setSpeed(tripData.getString("GPSSpeed"));
                    tripsList.add(trip);
                }
            } else {
                JSONObject tripData = tripsObj.getJSONObject("Trip");
                Trip trip = new Trip();
                // Pulling items from the array
                trip.setTripDestination(tripData.getString("TripDestination"));
                trip.setStartTime(tripData.getString("TripStartTime"));
                trip.setLastTrip(tripData.getBoolean("LastTripOfSchedule"));
                trip.setGpsLat(tripData.getString("Latitude"));
                trip.setGpsLong(tripData.getString("Longitude"));
                trip.setSpeed(tripData.getString("GPSSpeed"));
                tripsList.add(trip);
            }

            //Log.i("Routes", trips.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Trip> getTripsList() {
        return tripsList;
    }

}
