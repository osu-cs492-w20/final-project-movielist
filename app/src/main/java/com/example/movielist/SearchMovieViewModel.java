package com.example.movielist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movielist.data.MovieNameSearchResult;
import com.example.movielist.data.MovieSearchRepository;
import com.example.movielist.data.Status;

import java.util.List;

public class SearchMovieViewModel extends ViewModel {
    private MovieSearchRepository mRepository;
    private LiveData<List<MovieNameSearchResult>> mSearchResults;
    private LiveData<Status> mLoadingStatus;

    public SearchMovieViewModel(){
        mRepository = new MovieSearchRepository();
        mSearchResults = mRepository.getMovieSearchResults();
        mLoadingStatus = mRepository.getLoadingStatus();
        //TODO Animation loading
    }

    public void loadMovieSearchResults(String movieName){
        mRepository.loadMovieSearchResults(movieName);
    }

    public LiveData<List<MovieNameSearchResult>> getMovieSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
