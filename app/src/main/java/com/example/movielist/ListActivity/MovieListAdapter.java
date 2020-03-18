package com.example.movielist.ListActivity;

import com.example.movielist.R;
import com.example.movielist.data.Movies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>{
    private List<Movies> moviesList;
    private onMovieItemClickedListener mMovieClickListener;

    public interface onMovieItemClickedListener {
        void onMovieItemClicked(Movies movie);
    }

    public MovieListAdapter(onMovieItemClickedListener listener){
        mMovieClickListener = listener;
        moviesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_createdlist_movie_item, parent, false);
        return new MovieListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.MovieListViewHolder holder, int position) {
        String movieTitle = moviesList.get(position).movie_title;
        holder.bind(movieTitle);
    }

    /*public void addMovie(Movies movie){
        moviesList.add(0, movie);
        notifyDataSetChanged();
    }*/

    public void updateMovies(List<Movies> newMovies){
        moviesList = newMovies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(moviesList != null){
            return moviesList.size();
        } else {
            return 0;
        }
    }

    class MovieListViewHolder extends RecyclerView.ViewHolder {
        private TextView Title;

        public MovieListViewHolder (View itemView){
            super(itemView);
            Title = itemView.findViewById(R.id.tv_movie_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    mMovieClickListener.onMovieItemClicked(moviesList.get(getAdapterPosition()));
                    Log.d("OCL", "onClick: " + moviesList.get(getAdapterPosition()).movie_title);
                }
            });
        }

        void bind(String newMovieTitle){
            Title.setText(newMovieTitle);
        }

    }


}
