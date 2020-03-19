package com.example.movielist.ListMovieDetailView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.movielist.R;
import com.example.movielist.SavedListViewModel;
import com.example.movielist.data.Movies;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIES = "Movies";
    private SavedListViewModel savedVM;
    private Movies movie;

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

            TextView MovieTitleTV = findViewById(R.id.title_details);
            MovieTitleTV.setText(movie.movie_title);

            TextView MovieSubtitleTV = findViewById(R.id.subtitle_details);
            MovieSubtitleTV.setText("Language: " + movie.movie_language + ", Rating: " + movie.movie_votes + ", Runtime: " + movie.movie_length);

            TextView MovieDescriptionTV = findViewById(R.id.description_details);
            MovieDescriptionTV.setText(movie.movie_overview);

            EditText MovieNotesET = findViewById(R.id.ET_movie_detail_notes);
            MovieNotesET.setText(movie.movie_user_notes);

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

            RatingBar ratingBar = findViewById(R.id.rb_movie_list);
            if(movie.movie_completion_status){
                ratingBar.setVisibility(View.VISIBLE);
            } else {
                ratingBar.setVisibility(View.INVISIBLE);
            }

            CheckBox checkBox = findViewById(R.id.cb_movie_completion);
            checkBox.setChecked(movie.movie_completion_status);
        }
    }

    public void onCompletionStatusClicked(View view){
        movie.movie_completion_status = !movie.movie_completion_status;
        savedVM.updateMovie(movie);
        RatingBar ratingBar = findViewById(R.id.rb_movie_list);
        if(movie.movie_completion_status){
            ratingBar.setVisibility(View.VISIBLE);
        } else {
            ratingBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        savedVM.updateMovie(movie);
        Log.d("Movie", "onBackPressed: " + movie.movie_user_notes);
        super.onBackPressed();
    }
}
