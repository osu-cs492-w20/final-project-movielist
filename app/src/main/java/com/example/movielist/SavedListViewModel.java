package com.example.movielist;

import android.app.Application;
import android.util.Log;

import java.util.List;


import com.example.movielist.data.CreatedUserList;
import com.example.movielist.data.CreatedUserListRepo;
import com.example.movielist.data.Movies;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SavedListViewModel extends AndroidViewModel {
    private CreatedUserListRepo mRepository;

    public SavedListViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CreatedUserListRepo(application);
    }

    public void insertMovie(Movies movie){
        mRepository.insertMovie(movie);
    }

    public void insertUserList(CreatedUserList createdUserList){
        mRepository.insertList(createdUserList);
    }

    public void deleteMovie(Movies movie){
        mRepository.deleteMovie(movie);
    }

    public void deleteList(CreatedUserList createdUserList){
        mRepository.deleteList(createdUserList);
    }

    public LiveData<List<CreatedUserList>> getAllLists(){
        return mRepository.getAllLists();
    }

    public LiveData<List<Movies>> getAllMovies(){
        return mRepository.getAllMovies();
    }

    public LiveData<List<Movies>> getListOfMovies(int listID){
        return mRepository.getListOfMovies(listID);
    }

    public void getMoviesTest(){
        mRepository.getMoviesTest();
    }

}
