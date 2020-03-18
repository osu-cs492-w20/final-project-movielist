package com.example.movielist.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movielist.utility.MovieUtils;

import java.util.List;

public class MovieSearchRepository implements MovieSearchAPIAsync.Callback {
    private static final String TAG = MovieSearchRepository.class.getSimpleName();
    private MutableLiveData<List<MovieNameSearchResult>> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;

    private String mCurrentQuery;

    public MovieSearchRepository(){
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public LiveData<List<MovieNameSearchResult>> getMovieSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadMovieSearchResults(String query){
        if(shouldExecuteSearch(query)){
            mCurrentQuery = query;
            String url = MovieUtils.buildMovieNameSearchURL(query);
            mSearchResults.setValue(null);
            Log.d(TAG,"executing serach with url: " + url);
            mLoadingStatus.setValue(Status.LOADING);
            new MovieSearchAPIAsync(this).execute(url);
        } else {
            Log.d(TAG, "PLACEHOLDER");
        }
    }

    private boolean shouldExecuteSearch(String query){
        //TODO CACHEING?
        return true;
    }

    @Override
    public void onSearchFinished(List<MovieNameSearchResult> movieSearchResults) {
        mSearchResults.setValue(movieSearchResults);
        if (movieSearchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
