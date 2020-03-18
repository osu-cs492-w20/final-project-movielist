package com.example.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class SearchMovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_DETAIL = "MovieDetail";
    private String TAG = SearchMovieDetailActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_detail);
        Log.d(TAG,"In ACTIVITY NOW");


    }
}
