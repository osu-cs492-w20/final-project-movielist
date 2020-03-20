package com.example.movielist.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.movielist.utility.MovieUtils;
import com.example.movielist.utility.NetworkUtils;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MovieDetailAPIAsync extends AsyncTask<String,Void,String> {
    String TAG = "MovieDetailAPIAsync";
    private Callback mCallback;

    public interface Callback {
        void onSearchFinished(MovieDetails movieDetailsResult);
    }
    public MovieDetailAPIAsync(Callback callback){
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String searchResults = null;
        try {
            searchResults = NetworkUtils.doHttpGet(url);
            //Log.d(TAG,"Results from HTTPGET Detail" + searchResults);
        } catch (IOException e){
            e.printStackTrace();
        }
        return searchResults;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        MovieDetails movieDetailsResult = null;
        if(s != null){
            movieDetailsResult = MovieUtils.parseMovieDetailsJSON(s);
            Log.d(TAG, "MovieDetailResult " + movieDetailsResult.title + " " + movieDetailsResult.overview);
        }
        mCallback.onSearchFinished(movieDetailsResult);
    }
}
