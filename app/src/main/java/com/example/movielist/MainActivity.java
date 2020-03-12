package com.example.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;

import com.example.movielist.data.CreatedUserList;
import com.example.movielist.data.Movies;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SavedListViewModel SavedVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void testSQLQuieries(){

        SavedVM = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedListViewModel.class);

        Movies test1 = new Movies();
        CreatedUserList test2 = new CreatedUserList();
        test2.list_id = 321;
        test2.list_title = "MyLis1t12";
        test2.user_name = "Sean12311223";
        SavedVM.insertUserList(test2);
        test1.movie_title = "Banachocula - 2";
        test1.movie_id = 124534;
        test1.movie_list_id = 321;
        SavedVM.insertMovie(test1);



        SavedVM.insertMovie(test1);
        final Observer<List<Movies>> testObv = new Observer<List<Movies>>() {
            @Override
            public void onChanged(List<Movies> movies) {
                Log.d("TEST", "onChanged Movies: " + movies.get(0).movie_title);
            }
        };

        final Observer<List<CreatedUserList>> testObv2 = new Observer<List<CreatedUserList>>() {
            @Override
            public void onChanged(List<CreatedUserList> movies) {
                Log.d("TEST", "onChanged CreatedUserList: " + movies.get(0).list_title);
            }
        };

        test1.movie_list_id = 321;
        test1.movie_id = 1652;
        test1.movie_title = "BooBerryCrunch - 3";

        SavedVM.getAllMovies().observe(this, testObv);
        SavedVM.getListOfMovies(321).observe(this, testObv);
        SavedVM.getAllLists().observe(this, testObv2);

        //SavedVM.deleteList(test2);

        /*SavedVM.getAllMovies().observe(this, testObv);
        SavedVM.getListOfMovies(321).observe(this, testObv);
        SavedVM.getAllLists().observe(this, testObv2);*/
    }
}
