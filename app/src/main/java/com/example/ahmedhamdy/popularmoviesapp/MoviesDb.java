package com.example.ahmedhamdy.popularmoviesapp;

import org.parceler.Parcel;

/**
 * Created by ahmed hamdy on 10/3/2017.
 */

@Parcel

public class MoviesDb {

    public String title;
    public int voteAverage;
    public String posterPath;
    public String overView;
    public String realeseDate;
    public int movieId;
    public MoviesDb() {

    }



    public String getTitle() {
        return title;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public String getOverView() {
        return overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getRealeseDate() {
        return realeseDate;
    }

    public int getMovieId() {
        return movieId;
    }


}
