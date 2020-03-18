package com.example.movielist.data;

import android.os.AsyncTask;

import com.example.movielist.utility.MovieUtils;
import com.example.movielist.utility.NetworkUtils;

import java.io.IOException;
import java.util.List;


public class MovieSearchAPIAsync extends AsyncTask<String, Void, String> {
    String TAG = "MovieSearchAPIAsync";
    private Callback mCallback;

    public interface Callback {
        void onSearchFinished(List<MovieNameSearchResult> movieResults);
    }

    public MovieSearchAPIAsync(Callback callback){
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String searchResults = null;
        try {
            searchResults = NetworkUtils.doHttpGet(url);
            //Log.d(TAG,"Results from HTTPGET movieSearch" + searchResults);
        } catch (IOException e){
            e.printStackTrace();
        }
        return searchResults;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        List<MovieNameSearchResult> searchResults = null;
        if(s != null) {
            searchResults = MovieUtils.parseMovieNameJSON(s);
        }
        mCallback.onSearchFinished(searchResults);
        //ArrayList<MovieNameSearchResult> searchResultList = MovieUtils.parseMovieNameJSON(s);
        //System.out.println(Arrays.deepToString(searchResultList.toArray()));
        //testQueryTV.setText(s);
    }
}
