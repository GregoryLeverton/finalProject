package com.example.greg.octranspo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greg.finalproject.R;
import com.example.greg.octranspo.octranspo.BusStopSearch;
import com.example.greg.octranspo.octranspo.StopSearchResult;

import java.util.ArrayList;

public class SearchStopsActivity extends AppCompatActivity {

    private EditText searchStopTxt;
    private Button searchStopBtn;
    private ListView previouslySearchedStopsList;

    private ArrayList<StopSearchResult> previousSearches;

    private PreviousSearchesAdapter previousSearchesAdapter;

    private SQLiteDatabase db;

    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.octranspo_activity_search_stops);

        searchStopTxt = (EditText) findViewById(R.id.searchStopTxt);
        // Set searchStopTxt to previously searched string
        sp = this.getSharedPreferences("SearchStopsActivity", Context.MODE_PRIVATE);
        searchStopTxt.setText(sp.getString("LastSearch", ""));


        searchStopBtn = (Button) findViewById(R.id.searchStopBtn);
        previouslySearchedStopsList = (ListView) findViewById(R.id.previouslySearchedStopsList);

        searchStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Save to shared preferences
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("LastSearch", searchStopTxt.getText().toString());
                editor.apply();

                searchForStop(Short.parseShort(searchStopTxt.getText().toString()));
            }
        });

        previousSearches = new ArrayList<>();

        previousSearchesAdapter = new PreviousSearchesAdapter(this);

        previouslySearchedStopsList.setAdapter(previousSearchesAdapter);


        // DB Operations

        db = new PreviousSearchDatabaseHelper(this).getWritableDatabase();

        // Query db

        updateSearchesArray();


    }

    private void updateSearchesArray() {
        Cursor c = db.query(true, PreviousSearchDatabaseHelper.TABLE_NAME,
                new String[]{
                        PreviousSearchDatabaseHelper.KEY_ID,
                        PreviousSearchDatabaseHelper.KEY_NAME
                },
                null, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                short id = c.getShort(c.getColumnIndex(PreviousSearchDatabaseHelper.KEY_ID));
                String name = c.getString(c.getColumnIndex(PreviousSearchDatabaseHelper.KEY_NAME));

                StopSearchResult search = new StopSearchResult(id, name);

                if (!previousSearches.contains(search)) {
                    previousSearches.add(new StopSearchResult(id, name));
                }

            } while (c.moveToNext());
        }

        c.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateSearchesArray();

        previousSearchesAdapter.notifyDataSetChanged();

    }

    private void searchForStop(short stopCode) {

        // Intent to StopSearchResult with stopcode
        Intent showStopDetails = new Intent(SearchStopsActivity.this, StopDetailsActivity.class);
        showStopDetails.putExtra("stopId", stopCode);
        startActivityForResult(showStopDetails, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (responseCode == BusStopSearch.ROUTE_NOT_FOUND) {
            Toast.makeText(this, "Route Not Found!", Toast.LENGTH_LONG).show();
        }
    }

    private void deletePreviousSearchFromDatabase(short stopId, String stopName) {
        Log.i("Delete previous search", "" + stopId);

        db.delete(PreviousSearchDatabaseHelper.TABLE_NAME,
                PreviousSearchDatabaseHelper.KEY_ID + "=" +
                        String.valueOf(stopId), null);

        Snackbar.make(findViewById(R.id.CoordinatorLayout), "Bus Stop Deleted: " + stopName,
                Snackbar.LENGTH_SHORT)
                .show();

    }

    private void deletePreviousSearchListItem(StopSearchResult search, int arrayPosition) {

        final short id = search.getId();
        final String name = search.getStopName();
        final int position = arrayPosition;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Delete saved bus stop?")
                .setTitle("Please Confirm")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletePreviousSearchFromDatabase(id, name);
                        previousSearches.remove(position);
                        previousSearchesAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("No", null)
                .show();


    }


    private class PreviousSearchesAdapter extends ArrayAdapter<StopSearchResult> {
        private PreviousSearchesAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return previousSearches.size();
        }

        public StopSearchResult getItem(int position) {
            return previousSearches.get(position);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = SearchStopsActivity.this.getLayoutInflater();

            final StopSearchResult previousSearch = previousSearches.get(position);

            View listItem = inflater.inflate(R.layout.octranspo_previous_search_list_item, null);

            TextView stopId = (TextView) listItem.findViewById(R.id.previousSearchStopIdTxt);
            TextView stopName = (TextView) listItem.findViewById(R.id.previousSearchStopNameTxt);
            ImageView deleteBtn = (ImageView) listItem.findViewById(R.id.previousSearchdeleteBtn);

            stopId.setText(String.valueOf(previousSearch.getId()));
            stopName.setText(previousSearch.getStopName());

            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchForStop(previousSearch.getId());
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchStopsActivity.this.deletePreviousSearchListItem(previousSearch, position);
                }
            });


            return listItem;
        }

        public long getId(int position) {
            return previousSearches.get(position).getId();
        }
    }
}
