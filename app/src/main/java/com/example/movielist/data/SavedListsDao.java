package com.example.movielist.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface SavedListsDao {

    //Inserts entries from the SQLlite database
    @Insert
    void insert(Movies movies);

    @Insert
    void insert(CreatedUserList createdUserList);

    //Deletes entries from the SQLlite database
    @Delete
    void delete(Movies movies);

    @Delete
    void delete(CreatedUserList createdUserList);

    @Query("SELECT * FROM CreatedUserList")
    public LiveData<List<CreatedUserList>> getMovieLists();

    @Query("SELECT * FROM Movies")
    public LiveData<List<Movies>> getAllMovies();

    @Query("SELECT * FROM Movies WHERE Movies.movie_list_id=:movie_list_id")
    public LiveData<List<Movies>> getListOfMovies(final int movie_list_id);

    @Query("SELECT * FROM Movies")
    public List<Movies> getMoviesTest();

}
