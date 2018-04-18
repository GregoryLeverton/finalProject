package com.example.greg.octranspo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.greg.finalproject.R;
import com.example.greg.octranspo.octranspo.RouteSearch;
import com.example.greg.octranspo.octranspo.Trip;

import java.util.ArrayList;

/**
 * Created by jason on 02/04/18.
 */

public class RouteDetailsFragment extends Fragment {

    private ArrayList<Trip> tripsList;

    private TripListAdapter tripListAdapter;

    private ListView listView;

    private ProgressBar progressBar;

    private TextView noTrips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle info = getArguments();

        View view = inflater.inflate(R.layout.octranspo_fragment_route_details, container, false);

        String routeNum = info.getString("routeNum");
        String routeName = info.getString("routeName");
        String stopId = String.valueOf(info.getShort("stopId"));

        new RouteSearch() {
            @Override
            protected void onPostExecute(String args) {
                processData(getTrips());
            }
        }.execute(routeNum, routeName, stopId);

        //"95", "Barrhaven Centre", "3000"

        listView = (ListView)view.findViewById(R.id.fragment_route_details_list);

        tripsList = new ArrayList<>();

        tripListAdapter = new TripListAdapter(getActivity());
        listView.setAdapter(tripListAdapter);

        progressBar = view.findViewById(R.id.route_detail_progressBar);
        noTrips = view.findViewById(R.id.route_detail_notrips);


        return view;

    }

    private void processData(ArrayList<Trip> trips) {
        tripsList = trips;
        Log.i("RouteDetailsFragment", trips.size()+"");
        tripListAdapter.notifyDataSetChanged();

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        if(trips.size() == 0) {
            noTrips.setVisibility(TextView.VISIBLE);
        }
    }

    private class TripListAdapter extends ArrayAdapter<Trip> {
        public TripListAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return tripsList.size();
        }

        public Trip getItem(int position) {
            return tripsList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();

            View result = inflater.inflate(R.layout.octranspo_route_detail_list_item, null);

            Trip trip = tripsList.get(position);

            TextView route_detail_name = result.findViewById(R.id.route_detail_name);
            TextView route_detail_start = result.findViewById(R.id.route_detail_start);
            TextView route_detail_last = result.findViewById(R.id.route_detail_last_trip);
            TextView route_detail_location = result.findViewById(R.id.route_detail_coord);
            TextView route_detail_speed = result.findViewById(R.id.route_detail_speed);

            route_detail_name.setText(trip.getTripDestination());
            route_detail_start.setText(trip.getStartTime());
            route_detail_last.setText(String.valueOf(trip.isLastTrip()));
            route_detail_location.setText(trip.getGpsLat() + ", " + trip.getGpsLong());
            route_detail_speed.setText(trip.getSpeed());

            return result;
        }

        public long getItemId(int position) {
            return position;
        }

        public long getId(int position) {
            return position;
        }
    }

}
