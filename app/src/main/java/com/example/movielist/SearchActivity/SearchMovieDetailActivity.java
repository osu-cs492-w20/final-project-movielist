package com.example.movielist.SearchActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.movielist.CreatedUserListAdapter;
import com.example.movielist.ListActivity.ListActivity;
import com.example.movielist.R;
import com.example.movielist.SavedListViewModel;
import com.example.movielist.data.CreatedUserList;
import com.example.movielist.data.MovieDetails;
import com.example.movielist.data.Movies;
import com.example.movielist.data.Status;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieDetailActivity extends AppCompatActivity{
    public static final String EXTRA_MOVIE_DETAIL = "MovieDetail";
    private Integer movieIDForSearch;
    private String TAG = SearchMovieDetailActivity.class.getSimpleName();
    private SearchMovieDetailViewModel movieDetailVM;
    private ProgressBar mLoadingPB;
    private TextView mErrorMSGTV;
    private TextView movieTitle;
    private ImageView moviePoster;
    private MovieDetails movieDetails = new MovieDetails();

    private List<String> list_names;
    private SavedListViewModel savedVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_detail);
        Log.d(TAG, "In ACTIVITY NOW");
        movieTitle = findViewById(R.id.tv_movie_detail_title);


        savedVM = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedListViewModel.class);

        savedVM.getAllLists().observe(this, new Observer<List<CreatedUserList>>() {
            @Override
            public void onChanged(List<CreatedUserList> createdUserLists) {
                List<String> newList = new ArrayList<String>();
                for(int i = 0; i < createdUserLists.size();i++){
                    newList.add(createdUserLists.get(i).list_title);
                }
                list_names = newList;
            }
        });



        //Get Intent to grab MovieID in order to do API request
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_DETAIL)) {
            movieIDForSearch = (Integer) intent.getSerializableExtra(EXTRA_MOVIE_DETAIL);
            Log.d(TAG, "sent movieID from searchActivity: " + movieIDForSearch);

            //movieDetailVM
            movieDetailVM = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(SearchMovieDetailViewModel.class);
            movieDetailVM.getMovieDetailsSearchResults().observe(this, new Observer<MovieDetails>() {
                @Override
                public void onChanged(MovieDetails movieSearchResults) {
                    if(movieSearchResults != null && movieSearchResults.title != null) {
                        movieDetails = movieSearchResults;
                        Log.d(TAG,"got details for Movie" + movieSearchResults);
                        movieTitle.setText(movieSearchResults.title);
                    }
                }
            });


            movieDetailVM.loadMovieDetailSearchResults(movieIDForSearch);


            //movieDetailVM.getLoadingStatus().observe(this,new Observer<Status>(){
            //    @Override
            //    public void onChanged(Status status) {
            //        if (status == Status.LOADING) {
            //            mLoadingPB.setVisibility(View.VISIBLE);
            //        } else if (status == Status.SUCCESS) {
            //            mLoadingPB.setVisibility(View.INVISIBLE);
            //        } else {
            //            mLoadingPB.setVisibility(View.INVISIBLE);
            //        }
            //    }
            //});

        }
    }

    private void viewMovieOnIMDB () {

    }

    //TODO NEED TO IMPLEMENT THIS
    private void onAddMovieToList(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_movie:
                addMovie(this);
                return true;
            default:
                return false;
        }
    }
    private void addMovie(Context c) {
        String[] items = new String[list_names.size()];

        for(int i = 0; i < list_names.size(); i++){
            items[i] = list_names.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(c)
                .setTitle("Select a List");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Movies movie = new Movies();
                movie.movie_title = movieDetails.title;
                movie.movie_poster_URL = movieDetails.poster_path;
                movie.movie_imdb_link = movieDetails.imdb_id;
                movie.movie_release_date = movieDetails.release_date;
                movie.movie_overview = movieDetails.overview;
                movie.movie_language = movieDetails.original_language;
                movie.movie_votes = (int) movieDetails.vote_count;
                movie.movie_id = movieDetails.id;
                movie.movie_banner_URL = movieDetails.backdrop_path;
                Log.d(TAG, "onClick: "+movie.movie_banner_URL);

                movie.movie_list_title = list_names.get(i);
                savedVM.insertMovie(movie);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*    @Override
    public void onCreatedUserListsClick(CreatedUserList createdUserList) {
        Movies movie = new Movies();
        movie.movie_title = movieDetails.title;
        movie.movie_poster_URL = movieDetails.poster_path;
        movie.movie_imdb_link = movieDetails.imdb_id;
        movie.movie_release_date = movieDetails.release_date;
        movie.movie_overview = movieDetails.overview;
        movie.movie_language = movieDetails.original_language;
        movie.movie_votes = (int) movieDetails.vote_count;
        movie.movie_id = movieDetails.id;
        //movie.movie_banner_URL = movieDetails.;

        movie.movie_list_title = createdUserList.list_title;
        savedVM.insertMovie(movie);
    }*/
}
