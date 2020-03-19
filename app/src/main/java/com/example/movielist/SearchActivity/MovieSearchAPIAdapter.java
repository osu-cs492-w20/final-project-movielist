package com.example.movielist.SearchActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielist.R;
import com.example.movielist.data.MovieNameSearchResult;

import java.util.List;

public class MovieSearchAPIAdapter extends RecyclerView.Adapter<MovieSearchAPIAdapter.MovieSearchViewHolder>{
    private List<MovieNameSearchResult> movieSearchResults;
    private MovieSearchClickListener movieSearchClickListener;

    public interface MovieSearchClickListener{
        void onMovieClick();
    }

    public MovieSearchAPIAdapter(MovieSearchClickListener clickListener){
        movieSearchClickListener = clickListener;
    }

    public void updateSearchResults(List<MovieNameSearchResult> movieNameSearchResults) {
        movieSearchResults = movieNameSearchResults;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (movieSearchResults != null) {
            return movieSearchResults.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public MovieSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_search_result_item,parent,false);
        return new MovieSearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchViewHolder holder, int position) {
        holder.bind(movieSearchResults.get(position));
    }

    class MovieSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mSearchResultTV;

        public MovieSearchViewHolder(View itemView){
            super(itemView);
            mSearchResultTV = itemView.findViewById(R.id.tv_search_result);
            itemView.setOnClickListener(this);
        }

        public void bind(MovieNameSearchResult movieNameSearchResult) {
            mSearchResultTV.setText(movieNameSearchResult.title); //TODO add contents for the view here
        }
        @Override
        public void onClick(View v) {
            //TODO action here
        }

    }

}
