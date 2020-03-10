package com.example.movielist.data;
import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

public class ListWithMovies {
    @Embedded public CreatedUserList createdUserList;
    @Relation(
            parentColumn = "list_id",
            entityColumn = "movie_id",
            associateBy = @Junction(CreatedUserList_XRef.class)
    )
    public List<Movies> movies;
}
