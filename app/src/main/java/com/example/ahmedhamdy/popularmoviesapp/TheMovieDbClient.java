package com.example.ahmedhamdy.popularmoviesapp;

import android.content.Context;
import android.net.Uri;
import android.view.accessibility.AccessibilityManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ahmed hamdy on 10/1/2017.
 */

public class TheMovieDbClient {

    private final static String API_KEY = "api_key=d416681d044b30054b329be10cb8c861";
    private final static String BASE_URL ="https://api.themoviedb.org/3/movie/";
    private final static String SORT_BY_POPULAR ="popular?";
    private final static String SORT_BY_TOP_RATED ="top_rated?";
    private final static String BASE_VIDEOS_URL1 = "https://api.themoviedb.org/3/movie/";
    private final static String BASE_VIDEOS_URL2 = "/videos?";
    private final static String BASE_YOUTUBE_URL ="https://www.youtube.com/watch?v=";
    private final static String BASE_REVIEWS_URL = "/reviews?";
    public final static String POSTER_BASE_URL ="http://image.tmdb.org/t/p/w185/";


    private static ArrayList linksfinal = new ArrayList();
    private static  ArrayList reviewsarray = new ArrayList();

  //https://www.youtube.com/watch?v=<key of the movie selected> for movies



   //  get movies https://api.themoviedb.org/3/movie/popular?api_key=apikey

    // for reviews https://api.themoviedb.org/3/movie/{id}/reviews?api_key={api_key}

    // method for build the url
    public static String sortByTop() {
       String url = BASE_URL + SORT_BY_TOP_RATED + API_KEY;
        return url;
    }

    public static String sortByPopular() {
        String url = BASE_URL + SORT_BY_POPULAR + API_KEY;
        return url;
    }

/*
 method to get movies json array
 */
  public static void getJsonString(RequestQueue queue, Context context, final MoviesAdapter movieadapter, String sortBy){
       String url;
      if (sortBy=="popular"){
          url = sortByPopular();
      }
      else {
          url = sortByTop();
      }


    // Request a string response from the provided URL.
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
             JSONArray items = null;

            try {
                items = response.getJSONArray("results");
                // parse json array  into array of model objects
                ArrayList<MoviesDb> movies = fromJson(items);
                for(MoviesDb movie :movies){
                    movieadapter.add(movie);
                }
                movieadapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {


        }
    });
    queue.add(jsonObjectRequest);


}
/////////////////////////////////

    public static MoviesDb fromJson(JSONObject jsonObject){
        MoviesDb b = new MoviesDb();
        try {
            b.title = jsonObject.getString("title");
            b.overview = jsonObject.getString("overview");
            b.posterPath = POSTER_BASE_URL + jsonObject.getString("poster_path");
            b.realeseDate = jsonObject.getString("release_date");
            b.voteAverage = jsonObject.getInt("vote_average");
            b.movieId = jsonObject.getInt("id");


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
            MoviesDb movie = TheMovieDbClient.fromJson(movieJson);
            if(movie!=null){
                movies.add(movie);
            }
        }

        return movies;
    }
    //////////////////////////
////// for trialers ///////////////
public static String getVideosRequestUrl(int id){

      // https://api.themoviedb.org/3/movie/55/videos?api_key=d416681d044b30054b329be10cb8c861

      return BASE_VIDEOS_URL1 + id + BASE_VIDEOS_URL2 + API_KEY;
}

public static void  getTrailersKey(RequestQueue queue,Context context,String url){

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
          JSONArray results = null;
          ArrayList keys = new ArrayList();
            try {
                results = response.getJSONArray("results");
                for(int i=0; i<results.length();i++){

                   String key = results.getJSONObject(i).getString("key");
                    keys.add(key);
                }
                gettrailerlinks(keys);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });

    queue.add(jsonObjectRequest);

}

public static void gettrailerlinks(ArrayList keys){
    ArrayList links = new ArrayList();
    for (int i=0;i<keys.size();i++){
        links.add(i,BASE_YOUTUBE_URL+keys.get(i));

    }
 linksfinal = links;

}

    public static ArrayList getLinksfinal() {
        return linksfinal;
    }

    ////////////////////////////////////////////////////



    //////// for reviews ///////

   public static String reviewsUrl(int id){

      // https://api.themoviedb.org/3/movie/120/reviews?api_key=d416681d044b30054b329be10cb8c861

    return  BASE_VIDEOS_URL1 + String.valueOf(id) + BASE_REVIEWS_URL + API_KEY;
   }




    public static void getReviewsResponse(final Context context, RequestQueue queue, String url){


       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {


               JSONArray results = null;
               ArrayList reviews = new ArrayList();
               try {
                   results = response.getJSONArray("results");
                   for(int i=0;i<results.length();i++){
                       String review = results.getJSONObject(i).getString("content");
                       reviews.add(review);
                   }
               } catch (JSONException e) {
                   e.printStackTrace();

               }
               reviewsarray = reviews;


           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

               Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
           }
       });

       queue.add(jsonObjectRequest);
    }



    public static ArrayList getReviewsarray() {
        return reviewsarray;
    }

    //////////////////////////
}


