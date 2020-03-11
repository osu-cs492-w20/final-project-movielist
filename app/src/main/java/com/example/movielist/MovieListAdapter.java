package com.example.movielist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {

    //ViewHolder "onCreate"--------------------------------------------------------------------
    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }
    //onBindViewHolder--------------------------------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {

    }
    //getItemCount------------------------------------------------------------------------------
    @Override
    public int getItemCount() {
        return 0;
    }
    //MovieItemViewHolder------------------------------------------------------------------------
    class MovieItemViewHolder extends RecyclerView.ViewHolder{

        private TextView mMovieNameTV;
        private ImageView mMovieIconIV;

        public MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }



    }


}
