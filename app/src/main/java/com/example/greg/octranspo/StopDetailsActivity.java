package com.example.greg.octranspo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.greg.octranspo.octranspo.BusStopSearch;
import com.example.greg.octranspo.octranspo.Route;

import java.util.ArrayList;

public class StopDetailsActivity extends Activity {

    private short stopId;

    private int resultCode;
    private ArrayList<Route> routes;

    private ListView listView;
    private ProgressBar progressBar;
    private TextView stopName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.octranspo_activity_stop_details);

        listView = (ListView) findViewById(R.id.stopDetailRoutesList);
        progressBar = (ProgressBar) findViewById(R.id.stopDetailProgressBar);
        stopName = (TextView) findViewById(R.id.stopDetailStopNameTxt);

        stopId = this.getIntent().getShortExtra("stopId", (short) 0);

        Log.i("StopDetailsActivity", "StopID: " + stopId);

        new BusStopSearch() {
            @Override
            protected void onPostExecute(String args) {
                processData(getResultCode(), getRoutes(), getStopName());
            }
        }.execute(stopId);




        //* Get stopId from intent
        // Run octranspo api request
        // Parse results
        // Save id/stopname pair in database
        // Populate list with routes
    }

    private void processData(int resultCode, ArrayList<Route> routes, String stopName) {

        switch(resultCode) {
            case BusStopSearch.SUCCESS:
                this.routes = routes;
                saveBusStopToDatabase(stopName);

                progressBar.setVisibility(View.INVISIBLE);

                this.stopName.setText(stopName);

                listView.setAdapter(new RouteListAdapter(this));

                Log.i("StopDetailsActivity", "Parse complete, returned " + routes.size() + " routes");
                break;
            case BusStopSearch.ROUTE_NOT_FOUND:

                // Finish()
                // Send fail code back to parent activity
                Intent resultIntent = new Intent();
                setResult(BusStopSearch.ROUTE_NOT_FOUND, resultIntent);
                finish();


                Log.i("StopDetailsActivity", "Route not found");
                break;

        }


    }

    private void saveBusStopToDatabase(String stopName) {
        // Save stopId and stopName pair to DB
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = new PreviousSearchDatabaseHelper(this).getWritableDatabase();

        cv.put(PreviousSearchDatabaseHelper.KEY_ID, stopId);
        cv.put(PreviousSearchDatabaseHelper.KEY_NAME, stopName);

        db.insert(PreviousSearchDatabaseHelper.TABLE_NAME, "NullColumnName", cv);

        db.close();
    }


    private class RouteListAdapter extends ArrayAdapter<Route> {
        private RouteListAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return routes.size();
        }

        public Route getItem(int position) {
            return routes.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = StopDetailsActivity.this.getLayoutInflater();

            Route route = routes.get(position);

            View listItem = layoutInflater.inflate(R.layout.octranspo_route_list_item, null);

            TextView routeNumTxt = (TextView) listItem.findViewById(R.id.routeNumber);
            TextView routeNameTxt = (TextView) listItem.findViewById(R.id.routeName);

            //routeNumTxt.setText("sdfs");

            routeNumTxt.setText(String.valueOf(route.getRouteNumber()));
            routeNameTxt.setText(route.getRouteHeading());
            // todo make an arrow pointing in the route direction

            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo load route times fragment
                }
            });

            return listItem;
        }

        public long getId(int position) {
            return 0;
        }
    }

}
