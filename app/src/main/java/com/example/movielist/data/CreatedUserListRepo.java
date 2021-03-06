package com.example.movielist.data;
import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.database.sqlite.SQLiteConstraintException;

import com.example.movielist.SearchActivity.SearchActivity;

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

    public void updateMovie(Movies movie){
        new UpdateAsyncTaskMovies(mDAO).execute(movie);
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


    public LiveData<List<Movies>> getListOfMovies(String listID){
        return mDAO.getListOfMovies(listID);
    }

    private static class InsertAsyncTaskMovies extends AsyncTask<Movies, Void, Void>{
        private SavedListsDao mAsyncTaskDAO;
        private String TAG = SearchActivity.class.getSimpleName();

        InsertAsyncTaskMovies(SavedListsDao dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Movies... movies){
            try {
                mAsyncTaskDAO.insert(movies[0]);
            } catch (SQLiteConstraintException e) {
                Log.d(TAG, "doInBackground: ERROR with insertint Movies");
            }
            return null;
        }
    }

    private static class InsertAsyncTaskUserLists extends AsyncTask<CreatedUserList, Void, Void>{
        private SavedListsDao mAsyncTaskDAO;
        private String TAG = SearchActivity.class.getSimpleName();

        InsertAsyncTaskUserLists(SavedListsDao dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(CreatedUserList... createdUserLists){
            try {
                mAsyncTaskDAO.insert(createdUserLists[0]);
            } catch (SQLiteConstraintException e){
                Log.d(TAG, "doInBackground: ERROR with inserting createdUserList");
            }
            return null;
        }
    }

    private static class UpdateAsyncTaskMovies extends AsyncTask<Movies, Void, Void>{
        private SavedListsDao mAsyncTaskDAO;
        private String TAG = SearchActivity.class.getSimpleName();

        UpdateAsyncTaskMovies(SavedListsDao dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Movies... movies){
            try {
                mAsyncTaskDAO.update(movies[0]);
            } catch (SQLiteConstraintException e) {
                Log.d(TAG, "doInBackground: ERROR with updating Movies");
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
