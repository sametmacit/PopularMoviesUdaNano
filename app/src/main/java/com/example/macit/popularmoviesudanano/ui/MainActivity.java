package com.example.macit.popularmoviesudanano.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.macit.popularmoviesudanano.R;
import com.example.macit.popularmoviesudanano.adapter.MovieAdapter;
import com.example.macit.popularmoviesudanano.model.Movie;
import com.example.macit.popularmoviesudanano.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private String TAG = MainActivity.class.getSimpleName();

    private ArrayList<Movie> moviesArrayList;
    private ProgressBar pBar;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;

    private String sortBy = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView)
                findViewById(R.id.recyclerview_posters);

        pBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        moviesArrayList = new ArrayList<>();

        if(isOnline()) {

            final GridLayoutManager layoutManager =
                    new GridLayoutManager(this, getResources()
                            .getInteger(R.integer.num_of_columns));

            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);

            fetchMovieData();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void fetchMovieData() {
        new FetchMovieTask().execute(sortBy);
    }

    @Override
    public void OnClick(int clickedItem) {
        Movie movie = moviesArrayList.get(clickedItem);
        Context context = this;
        Class destinationClass = MovieDetailsActivity.class;
        Intent intentToStartMovieDetailsActivity = new Intent(context, destinationClass);
        intentToStartMovieDetailsActivity.putExtra(MovieDetailsActivity.MOVIE_EXTRA,movie);
        startActivity(intentToStartMovieDetailsActivity);
    }

    public class FetchMovieTask extends
            AsyncTask<String, String,ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            pBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            String sortOrder = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sortOrder);
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                moviesArrayList = NetworkUtils
                        .getMovieArrayList(jsonMovieResponse);

                return moviesArrayList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Movie> response) {
            pBar.setVisibility(View.INVISIBLE);

            mMovieAdapter = new MovieAdapter(moviesArrayList,getApplicationContext(),MainActivity.this);
            mRecyclerView.setAdapter(mMovieAdapter);
            mMovieAdapter.notifyDataSetChanged();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_top_rated:
                sortBy = "top_rated";
                fetchMovieData();
                return true;
            case R.id.sort_by_most_popular:
                sortBy = "popular";
                fetchMovieData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings,menu);
        return true;
    }
}
