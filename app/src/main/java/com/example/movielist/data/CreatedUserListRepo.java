package com.example.movielist.data;
import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.database.sqlite.SQLiteConstraintException;

import java.sql.SQLClientInfoException;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Entity;

@Entity
public class CreatedUserListRepo {
    private SavedListsDao mDAO;

    public CreatedUserListRepo(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mDAO = db.savedListsDao();
    }

    public void insertMovie(Movies movie){
        new InsertAsyncTaskMovies(mDAO).execute(movie);
    }

    public void insertList(CreatedUserList createdUserList){
        new InsertAsyncTaskUserLists(mDAO).execute(createdUserList);
    }

    public void deleteMovie(Movies movie){
        new DeleteAsyncTaskMovies(mDAO).execute(movie);
    }

    public void deleteList(CreatedUserList createdUserList){
        new DeleteAsyncTaskUserLists(mDAO).execute(createdUserList);
    }

    public LiveData<List<CreatedUserList>> getAllLists(){
        return mDAO.getMovieLists();
    }

    public LiveData<List<Movies>> getAllMovies(){
        return mDAO.getAllMovies();
    }

    public LiveData<List<Movies>> getListOfMovies(int listID){
        return mDAO.getListOfMovies(listID);
    }

    public List<Movies> getMoviesTest(){
        return mDAO.getMoviesTest();
    }

    private static class InsertAsyncTaskMovies extends AsyncTask<Movies, Void, Void>{
        private SavedListsDao mAsyncTaskDAO;
        InsertAsyncTaskMovies(SavedListsDao dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Movies... movies){
            try {
                mAsyncTaskDAO.insert(movies[0]);
            } catch (SQLiteConstraintException e) {
                Log.d("ERROR", "doInBackground: ERROR");
            }
            return null;
        }
    }

    private static class InsertAsyncTaskUserLists extends AsyncTask<CreatedUserList, Void, Void>{
        private SavedListsDao mAsyncTaskDAO;
        InsertAsyncTaskUserLists(SavedListsDao dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(CreatedUserList... createdUserLists){
            try {
                mAsyncTaskDAO.insert(createdUserLists[0]);
            } catch (SQLiteConstraintException e){
                Log.d("ERROR", "doInBackground: ERROR");
            }
            return null;
        }
    }

    private static class DeleteAsyncTaskMovies extends AsyncTask<Movies, Void, Void>{
        private SavedListsDao mAsyncTaskDAO;
        DeleteAsyncTaskMovies(SavedListsDao dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Movies... movies){
            mAsyncTaskDAO.delete(movies[0]);
            return null;
        }
    }

    private static class DeleteAsyncTaskUserLists extends AsyncTask<CreatedUserList, Void, Void>{
        private SavedListsDao mAsyncTaskDAO;
        DeleteAsyncTaskUserLists(SavedListsDao dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(CreatedUserList... createdUserLists){
            mAsyncTaskDAO.delete(createdUserLists[0]);
            return null;
        }
    }
}
