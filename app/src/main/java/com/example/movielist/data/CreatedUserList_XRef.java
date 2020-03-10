package com.example.movielist.data;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "CreatedUserList_XRef", primaryKeys = {"list_id","movie_id"})
public class CreatedUserList_XRef {

    @ColumnInfo(name = "list_id")
    public int list_id;

    @ColumnInfo(name = "movie_id")
    public int movie_id;

}
