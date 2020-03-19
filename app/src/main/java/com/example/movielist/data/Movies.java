package com.example.movielist.data;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = CreatedUserList.class,
        parentColumns = "list_title",
        childColumns = "movie_list_title",
        onDelete = CASCADE
), primaryKeys = {"movie_id", "movie_list_title"})
public class Movies implements Serializable {
    @NonNull
    @ColumnInfo(name = "movie_id")
    public int movie_id;

    @ColumnInfo(name = "movie_title")
    public String movie_title;

    @ColumnInfo(name = "movie_votes")
    public int movie_votes;

    @ColumnInfo(name = "movie_language")
    public String movie_language;

    @ColumnInfo(name = "movie_length")
    public String movie_length;

    @ColumnInfo(name = "movie_release_date")
    public String movie_release_date;

    @ColumnInfo(name = "movie_imdb_link")
    public String movie_imdb_link;

    @ColumnInfo(name = "movie_completion_status")
    public boolean movie_completion_status;

    @ColumnInfo(name = "movie_poster_URL")
    public String movie_poster_URL;

    @NonNull
    @ColumnInfo(name = "movie_list_title")
    public String movie_list_title;

    @ColumnInfo(name = "movie_overview")
    public String movie_overview;

    @ColumnInfo(name = "movie_user_notes")
    public String movie_user_notes;

    @ColumnInfo(name = "movie_user_rating")
    public float movie_user_rating;

}
