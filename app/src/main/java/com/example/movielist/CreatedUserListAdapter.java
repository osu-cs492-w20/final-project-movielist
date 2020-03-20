package com.example.movielist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielist.data.CreatedUserList;

import java.util.List;

public class CreatedUserListAdapter extends RecyclerView.Adapter<CreatedUserListAdapter.CreatedUserListViewHolder> {
    //All of our variables---------------------------------------------------------------------
    //private List<MovieDetails> mMovieDetailsItems;
    private List<CreatedUserList> mCreatedUserList;
    private CreatedUserListClickListener mCreatedUserListClickListener;

    //OnMovieDetailsItemClickListener Interface------------------------------------------------
    public interface CreatedUserListClickListener{
        void onCreatedUserListsClick(CreatedUserList createdUserList);
    }
    //MovieAdapter (Implements Click Listener)-------------------------------------------------
    public CreatedUserListAdapter(CreatedUserListClickListener clickListener){
        mCreatedUserListClickListener = clickListener;
    }
    //updateMovieDetailItems-------------------------------------------------------------------
    public void updateCreatedUserList(List<CreatedUserList> createdUserLists){
        mCreatedUserList = createdUserLists;
        notifyDataSetChanged();
    }
    //Get Item Count---------------------------------------------------------------------------
    @Override
    public int getItemCount() {
        if (mCreatedUserList != null) {
            return mCreatedUserList.size();
        } else {
            return 0;
        }
    }
    //ViewHolder "onCreate"--------------------------------------------------------------------
    @NonNull
    @Override
    public CreatedUserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new CreatedUserListViewHolder(itemView);
    }
    //onBindViewHolder--------------------------------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull CreatedUserListViewHolder holder, int position) {
        holder.bind(mCreatedUserList.get(position));
    }

    //Get list from adapter position------------------------------------------------------------
    public CreatedUserList returnListFromPosition(int position) {
        return mCreatedUserList.get(position);
    }

    //ListItemViewHolder------------------------------------------------------------------------
    class CreatedUserListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mCreatedUserListsTV;


        public CreatedUserListViewHolder(@NonNull View itemView) {
            super(itemView);

            mCreatedUserListsTV = itemView.findViewById(R.id.tv_list_name);

            itemView.setOnClickListener(this);
        }

        public void bind(CreatedUserList createdUserList){
            mCreatedUserListsTV.setText(createdUserList.list_title);
        }


        @Override
        public void onClick(View v) {
            CreatedUserList createdUserList = mCreatedUserList.get(getAdapterPosition());
            mCreatedUserListClickListener.onCreatedUserListsClick(createdUserList);
        }
    }


}
