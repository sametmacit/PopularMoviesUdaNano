package com.example.macit.popularmoviesudanano.util;

import android.net.Uri;
import android.util.Log;

import com.example.macit.popularmoviesudanano.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by macit on 12.03.2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL =
            "https://api.themoviedb.org/3/movie/";
    public static final String  BASE_POSTER_URI =
            "https://image.tmdb.org/t/p/w500";

    private static final String API_KEY = "tMDB API KEY";

    private static final String API_KEY_PARAM = "api_key";

    public static final String RESULTS = "results";
    public static final String POSTER = "poster_path";
    public static  final String BACKDROP = "backdrop_path";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String STATUS_CODE = "status_code";

    public static URL buildUrl(String sortOrder) {
        Uri builtUri = Uri.parse(BASE_URL + sortOrder).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection =
                (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<Movie> getMovieArrayList(String jsonMovieResponse) {
        JSONObject movieJson = null;
        ArrayList<Movie> moviesArrayList
                = new ArrayList<>();
        try {
            movieJson = new JSONObject(jsonMovieResponse);

            if (movieJson.has(STATUS_CODE)) {
                int errorCode = movieJson.getInt(STATUS_CODE);

                switch (errorCode) {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        return null;
                    default:
                        return null;
                }
            }
            JSONArray movieArray = movieJson.getJSONArray(RESULTS);

            for (int i = 0; i < movieArray.length(); i++) {
                Movie movie = new Movie();
                JSONObject movieData = movieArray.getJSONObject(i);
                movie.setPosterUrl(BASE_POSTER_URI + movieData.getString(POSTER));
                movie.setBackdropUrl(BASE_POSTER_URI + movieData.getString(BACKDROP));
                movie.setMovieOverview(movieData.getString(OVERVIEW));
                movie.setMovieReleaseDate(movieData.getString(RELEASE_DATE));
                movie.setMovieTitle(movieData.getString(ORIGINAL_TITLE));
                movie.setMovieVoteAverage(movieData.getString(VOTE_AVERAGE));

                moviesArrayList.add(movie);
            }
            return moviesArrayList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}