package com.example.movielist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielist.data.MovieDetails;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {
    //All of our variables---------------------------------------------------------------------
    private List<MovieDetails> mMovieDetailsItems;
    private OnMovieDetailsItemClickListener mMovieDetailsItemClickListener;

    //OnMovieDetailsItemClickListener Interface------------------------------------------------
    public interface OnMovieDetailsItemClickListener{

    }
    //MovieAdapter (Implements Click Listener)-------------------------------------------------
    public MovieListAdapter(OnMovieDetailsItemClickListener clickListener){
        mMovieDetailsItemClickListener = clickListener;
    }
    //updateMovieDetailItems-------------------------------------------------------------------
    public void updateMovieDetailItems(List<MovieDetails> movieDetailsItems){
        mMovieDetailsItems = movieDetailsItems;
        notifyDataSetChanged();
    }
    //Get Item Count---------------------------------------------------------------------------
    //Waiting to get correct object implemented.
    /*@Override
    public int getItemCount() {
        if (mForecastItems != null) {
            return mForecastItems.size();
        } else {
            return 0;
        }
    }*/
    //ViewHolder "onCreate"--------------------------------------------------------------------
    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieItemViewHolder(itemView);
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

        private TextView mMovieListTV;


        public MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }



    }


}
