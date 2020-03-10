package com.example.movielist.data;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "CreatedUserList")
public class CreatedUserList implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "list_id")
    public int list_id;

    @ColumnInfo(name = "list_title")
    public String list_title;

    @ColumnInfo(name = "user_name")
    public String user_name;
}
