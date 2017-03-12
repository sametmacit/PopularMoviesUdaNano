package com.example.macit.popularmoviesudanano.ui;

import android.content.Context;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macit.popularmoviesudanano.R;
import com.example.macit.popularmoviesudanano.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    Context mContext;
    TextView title,overview,vote_average,release_date;
    ImageView backdropImage,posterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Movie movie = getIntent().getExtras().getParcelable(MOVIE_EXTRA);


        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        vote_average = (TextView) findViewById(R.id.vote_average);
        release_date = (TextView) findViewById(R.id.release_date);
        backdropImage = (ImageView) findViewById(R.id.backdrop_url);
        posterImage = (ImageView) findViewById(R.id.poster_url);

        Picasso.with(mContext).load(movie.getBackdropUrl()).into(backdropImage);
        Picasso.with(mContext).load(movie.getPosterUrl()).into(posterImage);

        title.setText(movie.getMovieTitle());
        overview.setText(movie.getMovieOverview());
        vote_average.setText(movie.getMovieVoteAverage());
        release_date.setText(getString(R.string.releaseDate)+ movie.getMovieReleaseDate());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
