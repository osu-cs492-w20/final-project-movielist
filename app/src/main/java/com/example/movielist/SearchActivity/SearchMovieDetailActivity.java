package com.example.movielist.SearchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movielist.MainActivity;
import com.example.movielist.R;
import com.example.movielist.data.MovieDetails;
import com.example.movielist.data.Movies;
import com.example.movielist.data.Status;

import java.text.NumberFormat;
import java.util.List;

public class SearchMovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_DETAIL = "MovieDetail";
    private Integer movieIDForSearch;
    private String TAG = SearchMovieDetailActivity.class.getSimpleName();
    private SearchMovieDetailViewModel movieDetailVM;
    private ProgressBar mLoadingPB;
    private TextView mErrorMSGTV;
    private TextView movieTitle;
    private ImageView moviePoster;
    private String movieIMGURL;
    private TextView movieOverview;
    private Button movieIMDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_detail);
        movieTitle = findViewById(R.id.tv_movie_detail_title);
        moviePoster = findViewById(R.id.search_poster_detail);
        movieOverview = findViewById(R.id.tv_movie_detail_overview);
        movieIMDB = findViewById(R.id.search_IMDB);
        movieIMGURL = null;

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
                        Log.d(TAG,"got details for Movie" + movieSearchResults.toString());
                        String movieRevenue = movieSearchResults.revenue;
                        String titleFormat = movieSearchResults.title + "\n"+ "\n" + "Runtime: " + movieSearchResults.runtime + "min" + "\n" + "\n" + "Language: " + movieSearchResults.original_language + "\n" + "\n" + "Total Revenue: " + "\n" + "$" + movieRevenue
                                + "\n" + "\n" + "Released: "+ movieSearchResults.release_date;
                        movieTitle.setText(titleFormat);
                    }
                    if(movieSearchResults != null && movieSearchResults.poster_path != null){
                        movieIMGURL = "https://image.tmdb.org/t/p/w500" + movieSearchResults.poster_path;
                        Log.d(TAG,"Movie POSTER URL: " + movieIMGURL);
                        Glide.with(SearchMovieDetailActivity.this).load(movieIMGURL)
                                .placeholder(R.drawable.ic_crop_original_black_24dp)
                                .error(R.drawable.ic_crop_original_black_24dp)
                                .into(moviePoster);
                    }
                    if(movieSearchResults != null && movieSearchResults.overview != null){
                        String movieOverviewText = "Synopsis: " + "\n"  + movieSearchResults.overview;
                        movieOverview.setText(movieOverviewText);
                    }
                }
            });

            try {
                movieDetailVM.loadMovieDetailSearchResults(movieIDForSearch);
            } catch (Exception e) {
                e.printStackTrace();
            }
            movieIMDB.setBackgroundColor(Color.YELLOW);
            movieIMDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Uri imdb = Uri.parse();
                    //Log.d(TAG,"IMDB URI: " + imdb);
                    //Intent intent = new Intent(Intent.ACTION_VIEW,imdb);
                    //intent.setData(Uri.parse());
                    //if(intent.resolveActivity(getPackageManager()) != null){
                    //    startActivity(intent);
                    //}
                }
            });


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"IN onStart");
        if(movieIMGURL != null) {
            Log.d(TAG,"movieIMGURL not null" + movieIMGURL);
        }
        Glide.with(this).load(movieIMGURL)
                .placeholder(R.drawable.ic_crop_original_black_24dp)
                .error(R.drawable.ic_crop_original_black_24dp)
                .into(moviePoster);
    }
    private void viewMovieOnIMDB () {

    }
    //TODO NEED TO IMPLEMENT THIS
    private void onAddMovieToList(){

    }
}
