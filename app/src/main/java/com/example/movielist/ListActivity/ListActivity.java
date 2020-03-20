package com.example.movielist.ListActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.movielist.ListMovieDetailView.MovieDetailActivity;
import com.example.movielist.MainActivity;
import com.example.movielist.R;
import com.example.movielist.SavedListViewModel;
import com.example.movielist.data.CreatedUserList;
import com.example.movielist.data.Movies;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListActivity extends AppCompatActivity implements ListAdapter.onMovieItemClickedListener {
    private SavedListViewModel savedListViewModel;
    public static final String EXTRA_LIST_OBJECT = "CreatedUserList";
    private ListAdapter adapter;
    private RecyclerView moviesListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        getSupportActionBar().setElevation(0);
        //Removes back arrow in second activity because we have android native arrow.
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        Intent intent = getIntent();
        CreatedUserList createdUserList = (CreatedUserList)intent.getSerializableExtra(EXTRA_LIST_OBJECT);

        moviesListRV = findViewById(R.id.rv_list_of_movies);
        moviesListRV.setLayoutManager(new LinearLayoutManager(this));
        moviesListRV.setHasFixedSize(true);

        adapter = new ListAdapter(this);
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(moviesListRV);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            final Movies deletedMovie;
            deletedMovie = adapter.returnListFromPosition(position);
            switch (direction){
                case ItemTouchHelper.LEFT:
                    savedListViewModel.deleteMovie(deletedMovie);
                    adapter.notifyItemRemoved(position);
                    Snackbar.make(moviesListRV, deletedMovie.movie_title, Snackbar.LENGTH_LONG)
                            .setAction("Undo and Insert at Bottom", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    savedListViewModel.insertMovie(deletedMovie);
                                    // mCreatedUserListAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(ListActivity.this, R.color.colorPrimaryDark ))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };


    @Override
    public void onMovieItemClicked(Movies movie){
        Intent intent = new Intent(ListActivity.this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIES, movie);
        startActivity(intent);
    }
}
