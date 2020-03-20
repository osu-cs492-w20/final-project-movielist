package com.example.movielist.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movielist.utility.MovieUtils;

import java.util.List;

public class MovieDetailSearchRepository implements MovieDetailAPIAsync.Callback {
    private static final String TAG = MovieDetailSearchRepository.class.getSimpleName();
    private MutableLiveData<Status> mLoadingStatus;
    private MutableLiveData<MovieDetails> movieDetailsResults;
    private Integer currentMovieID;

    public MovieDetailSearchRepository(){
        movieDetailsResults = new MutableLiveData<>();
        movieDetailsResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public LiveData<MovieDetails> getMovieDetailSearchResults() {
        return movieDetailsResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadMovieDetailSearchResult(Integer movieID) {
        if(shouldExecuteSearch(movieID)) {
            currentMovieID = movieID;

            String url = MovieUtils.buildMovieSearchByID(movieID.toString());
            movieDetailsResults.setValue(null);
            Log.d(TAG, "executing movie detail search with: " + url);
            mLoadingStatus.setValue(Status.LOADING);
            new MovieDetailAPIAsync(this).execute(url);
        } else {
            Log.d(TAG, "PLACEHOLDER");
        }
    }

    private boolean shouldExecuteSearch(Integer movieID){
        //TODO CACHEING?
        return true;
    }

    @Override
    public void onSearchFinished(MovieDetails movieDetailsResult) {
        movieDetailsResults.setValue(movieDetailsResult);
        if(movieDetailsResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
