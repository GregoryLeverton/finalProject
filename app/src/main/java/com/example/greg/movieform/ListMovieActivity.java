package com.example.greg.movieform;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.greg.finalproject.R;


public class ListMovieActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);

        Button button1 = (Button)findViewById(R.id.quiz_button);





        Snackbar.make(findViewById(android.R.id.content), "Had a snack at Snackbar", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
        button1.setOnClickListener(e ->{
                    CharSequence text = "you pushed a button";// "Switch is Off"
                    int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off

                    Toast toast = Toast.makeText(getApplicationContext(), text, duration); //this is the ListActivity

                    toast.show();
                }
        );

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Place Holder")
                .setTitle("Place Holder")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}


