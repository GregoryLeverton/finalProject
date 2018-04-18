package com.example.greg.movieform;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greg.finalproject.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Tran on 2018-04-17.
 */

public class MovieFragment extends Fragment{

    private TextView titleView;
    private TextView actorsView;
    private TextView lengthView;
    private TextView descView;
    private TextView ratingView;


    private ImageView posterView;
    private Button updateBttn;

    Button deleteBttn;
    Boolean hasFrameLayout;
    Bundle movieDetails;





    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_details,container, false);
        this.movieDetails =getArguments();

        //determine if there is a side frame
        this.hasFrameLayout = movieDetails.getBoolean("hasFrameLayout");

        //get move details view from layout xml
        this.titleView = view.findViewById(R.id.title);
        this.actorsView = view.findViewById(R.id.actor);
        this.lengthView = view.findViewById(R.id.length);
        this.descView = view.findViewById(R.id.desc);
        this.ratingView = view.findViewById(R.id.rating);
        this.posterView = view.findViewById(R.id.poster);



        //assign Buttons
        //this.deleteBttn = view.findViewById(R.id.deleteButton);
        this.updateBttn = view.findViewById(R.id.updateButton);

        //assign movie detail texts to views
        this.titleView.setText(movieDetails.getString("title"));
        this.actorsView.setText(movieDetails.getString("actors"));
        this.lengthView.setText(movieDetails.getString("length"));
        this.descView.setText(movieDetails.getString("desc"));
        this.ratingView.setText(String.valueOf(movieDetails.getInt("rating")));

        DownloadImageAsyncTask imgAsyncTask = new DownloadImageAsyncTask(posterView,movieDetails.getString("url"));
        imgAsyncTask.execute();

        this.updateBttn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent intent = new Intent(getActivity(),UpdateMovieActivity.class);
                intent.putExtras(getArguments());


                //!!! important getACtivity() gets the activity that encapsulates this fragment
                if(getMovieDetails().getBoolean("hasFrameLayout")) {
                    getActivity().startActivityForResult(intent, 400);
                }else{
                    intent.putExtra("id",movieDetails.getInt("id"));
                    startActivityForResult(intent,400);
                }

            }
        });


        return view;
    }
//this is invoked when result is returned from movieDetailsActivity not fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //400 refers to the result returned from Movie update;
        if (requestCode == 400 && resultCode == RESULT_OK) {
            Bundle movieDetailsBundle = data.getExtras();


            Movie movie = MovieDatabaseHelper.getMovieFromDB(getContext(),movieDetailsBundle.getInt("id"));

            this.titleView.setText(movie.getTitle());
            this.actorsView.setText(movie.getActors());
            this.lengthView.setText(movie.getLength());
            this.descView.setText(movie.getDesc());
            this.ratingView.setText(String.valueOf(movie.getRating()));
            DownloadImageAsyncTask imgAsyncTask = new DownloadImageAsyncTask(this.posterView,movie.getUrl());
            imgAsyncTask.execute();

        }
    };

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitleView(TextView titleView) {
        this.titleView = titleView;
    }

    public TextView getActorsView() {
        return actorsView;
    }

    public void setActorsView(TextView actorsView) {
        this.actorsView = actorsView;
    }

    public TextView getLengthView() {
        return lengthView;
    }

    public void setLengthView(TextView lengthView) {
        this.lengthView = lengthView;
    }

    public TextView getDescView() {
        return descView;
    }

    public void setDescView(TextView descView) {
        this.descView = descView;
    }

    public TextView getRatingView() {
        return ratingView;
    }

    public void setRatingView(TextView ratingView) {
        this.ratingView = ratingView;
    }

    public ImageView getPosterView() {
        return posterView;
    }

    public void setPosterView(ImageView posterView) {
        this.posterView = posterView;
    }

    public Button getUpdateBttn() {
        return updateBttn;
    }

    public void setUpdateBttn(Button updateBttn) {
        this.updateBttn = updateBttn;
    }

    public Button getDeleteBttn() {
        return deleteBttn;
    }

    public void setDeleteBttn(Button deleteBttn) {
        this.deleteBttn = deleteBttn;
    }

    public Boolean getHasFrameLayout() {
        return hasFrameLayout;
    }

    public void setHasFrameLayout(Boolean hasFrameLayout) {
        this.hasFrameLayout = hasFrameLayout;
    }

    public Bundle getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(Bundle movieDetails) {
        this.movieDetails = movieDetails;
    }


}
