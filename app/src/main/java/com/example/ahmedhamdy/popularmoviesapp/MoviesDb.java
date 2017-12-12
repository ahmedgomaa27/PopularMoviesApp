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

    private String title;
    private int voteAverage;
    private String posterPath;
    private String overview;
    private String realeseDate;
    private final static String POSTER_BASE_URL ="http://image.tmdb.org/t/p/w185/";
    private int ID ;


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
        return POSTER_BASE_URL + posterPath;
    }

    public String getRealeseDate() {
        return realeseDate;
    }
    public  int getID(){
        return ID;
    }


    public static MoviesDb fromJson(JSONObject jsonObject){
        MoviesDb b = new MoviesDb();
        try {
            b.title = jsonObject.getString("title");
            b.overview = jsonObject.getString("overview");
            b.posterPath = jsonObject.getString("poster_path");
            b.realeseDate = jsonObject.getString("release_date");
            b.voteAverage = jsonObject.getInt("vote_average");
            b.ID = jsonObject.getInt("id");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return b;
    }

    //Decodes array of box office movie json results into movie model objects
    public static ArrayList<MoviesDb> fromJson(JSONArray jsonArray){

        ArrayList<MoviesDb> movies = new ArrayList<MoviesDb>(jsonArray.length());
         // get every movie Jsonobject to convert to movie data model
        for (int i=0; i< jsonArray.length();i++){
            JSONObject movieJson = null;
            try {
                movieJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MoviesDb movie = MoviesDb.fromJson(movieJson);
            if(movie!=null){
                movies.add(movie);
            }
        }

        return movies;
    }
}
