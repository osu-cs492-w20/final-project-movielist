package com.example.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.movielist.data.MovieDetails;
import com.example.movielist.data.MovieNameSearchResult;
import com.example.movielist.utility.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {
    private TextView testQueryTV;
    private MovieUtils movieUtils;
    private String TAG = "SearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        //ArrayList<MovieNameSearchResult> query =
        testQueryTV = findViewById(R.id.tv_search_txt);
        //testQueryTV.setText(query.toString());
        doMovieSearch("Batman");
        doMovieDetailSearch("272");
        doMovieImgSearch("272");
    }


    private void doMovieSearch(String search) {
        String url = MovieUtils.buildMovieNameSearchURL(search);
        Log.d(TAG, "Doing API query with: " + url);
        new MovieSearchTask().execute(url);
    }
    private void doMovieDetailSearch(String search) {
        String url = MovieUtils.buildMovieSearchByID(search);
        Log.d(TAG,"Doing detail search by ID with: " + url);
        new MovieSearchDetailTask().execute(url);
    }

    private void doMovieImgSearch(String movieID){
        String url = MovieUtils.buildMovieImageSearchByMovieID(movieID);
        Log.d(TAG,"Doing img searches by movie ID with: " + url);
        new MovieImgSearchByMovieID().execute(url);
    }
    public class MovieSearchTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(url);
                Log.d(TAG,"Results from HTTPGET movieSearch" + searchResults);
            } catch (IOException e){
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            ArrayList<MovieNameSearchResult> searchResultList = MovieUtils.parseMovieNameJSON(s);
            System.out.println(Arrays.deepToString(searchResultList.toArray()));
            testQueryTV.setText(s);
        }
    }
    public class MovieSearchDetailTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(url);
                Log.d(TAG,"Results from HTTPGET Detail" + searchResults);
            } catch (IOException e){
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
           MovieDetails movieDetails = MovieUtils.parseMovieDetailsJSON(s);
           Log.d(TAG, "MovieDetailResult " + movieDetails.title + " " + movieDetails.overview);
           testQueryTV.setText(s);
        }
    }
    public class MovieImgSearchByMovieID extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(url);
                Log.d(TAG,"Results from HTTPGET movieIMGID" + searchResults);
            } catch (IOException e){
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            testQueryTV.setText(s);
        }
    }
}
