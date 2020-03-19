package com.example.movielist.SearchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movielist.R;
import com.example.movielist.data.MovieDetails;
import com.example.movielist.data.Status;

import java.util.List;

public class SearchMovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_DETAIL = "MovieDetail";
    private Integer movieIDForSearch;
    private String TAG = SearchMovieDetailActivity.class.getSimpleName();
    private SearchMovieDetailViewModel movieDetailVM;
    private ProgressBar mLoadingPB;
    private TextView mErrorMSGTV;
    private TextView movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_detail);
        Log.d(TAG, "In ACTIVITY NOW");
        movieTitle = findViewById(R.id.tv_movie_detail_title);

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
}
