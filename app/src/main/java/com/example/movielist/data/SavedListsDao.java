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

    @Insert
    void insert(Movies movies);

    @Insert
    void insert(CreatedUserList createdUserList);

    @Insert
    void insert(ListWithMovies listWithMovies);
    
    @Insert
    void insert(CreatedUserList_XRef createdUserList_xRef);

    //Deletes entries from the SQLlite database
    @Delete
    void Delete(Movies movies);

    @Delete
    void Delete(CreatedUserList createdUserList);

    @Delete
    void Delete(ListWithMovies listWithMovies);

    @Delete
    void Delete(CreatedUserList_XRef createdUserList_xRef);

    @Transaction
    @Query("SELECT * FROM CreatedUserList")
    public List<ListWithMovies> getMovieLists();



}
