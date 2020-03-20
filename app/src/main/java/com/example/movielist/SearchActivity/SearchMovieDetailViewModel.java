package com.example.movielist.SearchActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movielist.data.MovieDetailSearchRepository;
import com.example.movielist.data.MovieDetails;
import com.example.movielist.data.Status;

public class SearchMovieDetailViewModel extends ViewModel {
    private MovieDetailSearchRepository movieDetailSearchRepository;
    private LiveData<MovieDetails> movieDetailsSearchResults;
    private LiveData<Status> mLoadingStatus;

    public SearchMovieDetailViewModel(){
        movieDetailSearchRepository = new MovieDetailSearchRepository();
        movieDetailsSearchResults = movieDetailSearchRepository.getMovieDetailSearchResults();
        mLoadingStatus = movieDetailSearchRepository.getLoadingStatus();
    }

    public void loadMovieDetailSearchResults(Integer movieID) {
        movieDetailSearchRepository.loadMovieDetailSearchResult(movieID);
    }

    public LiveData<MovieDetails> getMovieDetailsSearchResults() {
        return movieDetailsSearchResults;
    }

    public LiveData<Status> getLoadingStatus(){
        return mLoadingStatus;
    }
}
