package com.example.macit.popularmoviesudanano.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macit on 12.03.2017.
 */
public class Movie implements Parcelable {
    private String posterUrl;
    private String backdropUrl;
    private String movieOverview;
    private String movieReleaseDate;
    private String movieVoteAverage;
    private String movieTitle;

    protected Movie(Parcel in) {
        posterUrl = in.readString();
        backdropUrl = in.readString();
        movieOverview = in.readString();
        movieReleaseDate = in.readString();
        movieVoteAverage = in.readString();
        movieTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterUrl);
        dest.writeString(backdropUrl);
        dest.writeString(movieOverview);
        dest.writeString(movieReleaseDate);
        dest.writeString(movieVoteAverage);
        dest.writeString(movieTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Movie(String posterUrl, String backdropUrl, String movieOverview, String movieReleaseDate, String movieVoteAverage, String movieTitle) {
        this.posterUrl = posterUrl;
        this.backdropUrl = backdropUrl;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.movieVoteAverage = movieVoteAverage;
        this.movieTitle = movieTitle;
    }

    public Movie(){};



    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public void setMovieVoteAverage(String movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }
}
