package com.example.movielist.ListMovieDetailView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movielist.R;
import com.example.movielist.SavedListViewModel;
import com.example.movielist.data.MovieDetailAPIAsync;
import com.example.movielist.data.Movies;

import java.io.InputStream;
import java.net.URL;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIES = "Movies";

    private Movies movie;
    private SavedListViewModel savedVM;

    private LinearLayout llBody;
    private ImageView ivBanner;
    private TextView MovieTitleTV;
    private ImageView ivPoster;
    private TextView MovieSubtitleTV;
    private Button MovieIMdbB;
    private TextView MovieDescriptionTV;
    private EditText MovieNotesET;
    private CheckBox checkBox;
    private RatingBar ratingBar;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        savedVM = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedListViewModel.class);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_MOVIES)){
            movie = (Movies)intent.getSerializableExtra(EXTRA_MOVIES);

            //Drawable Poster = LoadImageFromWebOperations("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
            ivBanner = findViewById(R.id.movie_banner);
            //ivBanner.setImageAlpha(200);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original" + movie.movie_banner_URL)
                    //.load(movie.movie_poster_URL) //TODO Uncomment this and reomove above line when URL functionality is present for poster path
                    //.override(900,500)
                    .centerCrop()
                    .placeholder(R.drawable.ic_crop_original_black_24dp)
                    .error(R.drawable.ic_crop_original_black_24dp)
                    .into(ivBanner);

            ivPoster = findViewById(R.id.movie_poster);
            Glide.with(this)
                    .load("http://image.tmdb.org/t/p/w185/" + movie.movie_poster_URL)
                    //.load(movie.movie_poster_URL) //TODO Uncomment this and reomove above line when URL functionality is present for poster path
                    .override(250,350)
                    .centerCrop()
                    .placeholder(R.drawable.ic_crop_original_black_24dp)
                    .error(R.drawable.ic_crop_original_black_24dp)
                    .into(ivPoster);

            llBody = findViewById(R.id.ll_list_detail);

            MovieTitleTV = findViewById(R.id.title_details);
            MovieTitleTV.setText(movie.movie_title);
            MovieTitleTV.setTextColor(Color.WHITE);

            MovieSubtitleTV = findViewById(R.id.subtitle_details);
            MovieSubtitleTV.setText("Language: " + movie.movie_language + ", Rating: " + movie.movie_votes + ", Release-Date: " + movie.movie_release_date);
            MovieSubtitleTV.setTextColor(Color.WHITE);

            MovieIMdbB = findViewById(R.id.imdb_link_movie_details);
            MovieIMdbB.setTextColor(Color.BLACK);
            MovieIMdbB.setBackgroundColor(Color.YELLOW);
            MovieIMdbB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(movie.movie_imdb_link));
                    startActivity(intent);
                }
            });


            MovieDescriptionTV = findViewById(R.id.description_details);
            MovieDescriptionTV.setText(movie.movie_overview);
            MovieDescriptionTV.setTextColor(Color.WHITE);

            MovieNotesET = findViewById(R.id.ET_movie_detail_notes);
            MovieNotesET.setText(movie.movie_user_notes);
            MovieNotesET.setTextColor(Color.WHITE);
            MovieNotesET.setHintTextColor(Color.WHITE);

            MovieNotesET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    movie.movie_user_notes = editable.toString();
                }
            });

            checkBox = findViewById(R.id.cb_movie_completion);
            checkBox.setChecked(movie.movie_completion_status);
            checkBox.setTextColor(Color.WHITE);

            ratingBar = findViewById(R.id.rb_movie_list);
            ratingBar.setRating(movie.movie_user_rating);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    movie.movie_user_rating = v;
                }
            });
            if(movie.movie_completion_status){
                ratingBar.setVisibility(View.VISIBLE);
            } else {
                ratingBar.setVisibility(View.INVISIBLE);
            }

        }
    }


    public void onCompletionStatusClicked(View view){
        movie.movie_completion_status = !movie.movie_completion_status;
        savedVM.updateMovie(movie);
        //RatingBar ratingBar = findViewById(R.id.rb_movie_list);
        if(movie.movie_completion_status){
            ratingBar.setVisibility(View.VISIBLE);
        } else {
            ratingBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        savedVM.updateMovie(movie);
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        savedVM.updateMovie(movie);
        onBackPressed();
        return true;
    }
}
