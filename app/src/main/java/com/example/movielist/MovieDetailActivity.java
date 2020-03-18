package com.example.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.movielist.data.Movies;

public class MovieDetailActivity extends AppCompatActivity{
    public static final String EXTRA_MOVIES = "Movies";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_MOVIES)){
            Movies movie = (Movies)intent.getSerializableExtra(EXTRA_MOVIES);

            TextView MovieTitleTV = findViewById(R.id.title_details);
            MovieTitleTV.setText(movie.movie_title);

            TextView MovieSubtitleTV = findViewById(R.id.subtitle_details);
            MovieSubtitleTV.setText("Language: " + movie.movie_language + "; Rating: " + movie.movie_votes);

            TextView MovieDescriptionTV = findViewById(R.id.description_details);
            //MovieDescriptionTV.setText()
        }
    }
}
