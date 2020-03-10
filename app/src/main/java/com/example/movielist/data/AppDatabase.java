package com.example.movielist.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CreatedUserList.class, CreatedUserList_XRef.class, ListWithMovies.class, Movies.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SavedListsDao savedListsDao();
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "MovieList_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
