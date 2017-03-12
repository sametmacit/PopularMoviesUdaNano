package com.example.macit.popularmoviesudanano.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.macit.popularmoviesudanano.R;
import com.example.macit.popularmoviesudanano.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by macit on 12.03.2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private ArrayList<Movie> movies;
    private Context mContext;
    private final MovieAdapterOnClickHandler mClickHandler;
    public interface MovieAdapterOnClickHandler{
        void OnClick(int clickedItem);
    }



    public MovieAdapter(ArrayList<Movie> movies, Context context, MovieAdapterOnClickHandler clickHandler) {
        this.movies = movies;
        this.mContext = context;
        this.mClickHandler = clickHandler;
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster_row,parent,false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.with(mContext).load(movie.getPosterUrl()).into(holder.posterView);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        ImageView posterView;
        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            posterView = (ImageView) itemView.findViewById(R.id.posterImage);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.OnClick(adapterPosition);
        }
    }
}
