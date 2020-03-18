package com.example.movielist.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.movielist.utility.NetworkUtils;

import java.io.IOException;
import java.util.List;

public class MovieIMGAPIAsync extends AsyncTask<String,Void,String> {
    String TAG = "MovieIMGAPIAsync";
    private MovieSearchAPIAsync.Callback mCallback;

    public interface Callback {
        void onSearchFinished(List<MovieDetails> movieResults);
    }
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
        //TODO FIX THIS WHEN IMPLEMENTING
        //testQueryTV.setText(s);
    }
}

