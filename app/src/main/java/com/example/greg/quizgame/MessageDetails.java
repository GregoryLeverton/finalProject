package com.example.greg.quizgame;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.greg.finalproject.R;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle bundle = getIntent().getBundleExtra("ChatItem");
        MessageFragment myFragment = new MessageFragment();
        myFragment.setArguments( bundle);
       FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, myFragment).commit();
    }
}
