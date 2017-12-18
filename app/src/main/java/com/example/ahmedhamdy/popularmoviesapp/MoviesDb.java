package com.example.ahmedhamdy.popularmoviesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by ahmed hamdy on 10/3/2017.
 */

@Parcel

public class MoviesDb {

    public  MoviesDb(){

    }

    public int id;
    public String title;
    public int voteAverage;
    public String posterPath;
    public String overview;
    public String realeseDate;
    public int movieId ;

    public int getId(){
        return id;
    }


    public String getTitle() {
        return title;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return  posterPath;
    }

    public String getRealeseDate() {
        return realeseDate;
    }
    public  int getMovieId(){
        return movieId;
    }





}
