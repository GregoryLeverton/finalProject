package com.example.greg.octranspo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.greg.finalproject.R;

public class RouteDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.octranspo_activity_route_details);

        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RouteDetailsFragment fragment = new RouteDetailsFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "routeDetails");
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }
}
