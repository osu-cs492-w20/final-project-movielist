package com.example.movielist.SearchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movielist.data.MovieDetails;
import com.example.movielist.R;
import com.example.movielist.data.MovieNameSearchResult;
import com.example.movielist.data.Status;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements MovieSearchAPIAdapter.MovieSearchClickListener {
    private String TAG = SearchActivity.class.getSimpleName();

    private EditText searchMovieET;
    private RecyclerView movieSearchResultsRV;
    private ProgressBar mLoadingPB;
    private TextView mErrorMSGTV;

    private MovieSearchAPIAdapter movieSearchAdapter;
    private SearchMovieViewModel searchMovieVM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchMovieET = findViewById(R.id.et_search_box);
        movieSearchResultsRV = findViewById(R.id.rv_search_results);

        movieSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        movieSearchResultsRV.setHasFixedSize(true);

        movieSearchAdapter = new MovieSearchAPIAdapter(this);
        movieSearchResultsRV.setAdapter(movieSearchAdapter);

        mLoadingPB = findViewById(R.id.pb_loading_indicator);
        mErrorMSGTV = findViewById(R.id.tv_error_message);
        searchMovieVM = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(SearchMovieViewModel.class);
        searchMovieVM.getMovieSearchResults().observe(this, new Observer<List<MovieNameSearchResult>>() {
            @Override
            public void onChanged(List<MovieNameSearchResult> movieSearchResults) {
                movieSearchAdapter.updateSearchResults(movieSearchResults);
            }
        });

        searchMovieVM.getLoadingStatus().observe(this, new Observer<Status>(){
            @Override
            public void onChanged(Status status) {
                if (status == Status.LOADING) {
                   mLoadingPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingPB.setVisibility(View.INVISIBLE);
                    movieSearchResultsRV.setVisibility(View.VISIBLE);
                    mErrorMSGTV.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingPB.setVisibility(View.INVISIBLE);
                    movieSearchResultsRV.setVisibility(View.INVISIBLE);
                    mErrorMSGTV.setVisibility(View.VISIBLE);
                }
            }
        });

        Button searchButton = findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchMovieET.getText().toString();
                if (!TextUtils.isEmpty(searchQuery)) {
                    doMovieSearch(searchQuery);
                }
            }
        });

    }

    private void doMovieSearch(String search) {
        searchMovieVM.loadMovieSearchResults(search);
    }

    @Override
    public void onMovieClick(Integer movieID) {
        Log.d(TAG,"Clicking movie");
        Intent intent = new Intent(this, SearchMovieDetailActivity.class);
        intent.putExtra(SearchMovieDetailActivity.EXTRA_MOVIE_DETAIL, movieID);
        startActivity(intent);

    }
}
