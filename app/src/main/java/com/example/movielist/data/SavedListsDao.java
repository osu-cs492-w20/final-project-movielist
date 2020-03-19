package com.example.movielist.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public interface SavedListsDao {

    //Inserts entries from the SQLlite database
    @Insert
    void insert(Movies movies);

    @Insert
    void insert(CreatedUserList createdUserList);

    @Update
    void update(Movies movies);

    //Deletes entries from the SQLlite database
    @Delete
    void delete(Movies movies);

    @Delete
    void delete(CreatedUserList createdUserList);

    @Query("SELECT * FROM CreatedUserList")
    public LiveData<List<CreatedUserList>> getMovieLists();

    @Query("SELECT * FROM Movies WHERE Movies.movie_list_title=:movie_list_title")
    public LiveData<List<Movies>> getListOfMovies(final String movie_list_title);

}
