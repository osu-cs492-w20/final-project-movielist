package com.example.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.movielist.data.CreatedUserList;
import com.example.movielist.data.Movies;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListActivity extends AppCompatActivity implements MovieListAdapter.onMovieItemClickedListener {
    private SavedListViewModel savedListViewModel;
    public static final String EXTRA_LIST_OBJECT = "CreatedUserList";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Intent intent = getIntent();
        CreatedUserList createdUserList = (CreatedUserList)intent.getSerializableExtra(EXTRA_LIST_OBJECT);

        RecyclerView moviesListRV = findViewById(R.id.rv_list_of_movies);
        moviesListRV.setLayoutManager(new LinearLayoutManager(this));
        moviesListRV.setHasFixedSize(true);

        final MovieListAdapter adapter = new MovieListAdapter(this);
        moviesListRV.setAdapter(adapter);

        savedListViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedListViewModel.class);

        if(intent != null && intent.hasExtra(EXTRA_LIST_OBJECT)) {
            TextView listTitle = findViewById(R.id.tv_list_title);
            listTitle.setText(createdUserList.list_title);

            savedListViewModel.getListOfMovies(createdUserList.list_title).observe(this, new Observer<List<Movies>>() {
                @Override
                public void onChanged(List<Movies> movies) {
                    adapter.updateMovies(movies);
                }
            });
        }
    }

    @Override
    public void onMovieItemClicked(Movies movie){

    }
}
