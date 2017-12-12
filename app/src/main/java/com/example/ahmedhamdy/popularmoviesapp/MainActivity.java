package com.example.ahmedhamdy.popularmoviesapp;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ahmedhamdy.popularmoviesapp.database.FavoriteMovies;
import com.example.ahmedhamdy.popularmoviesapp.database.FavoritemoviesTable;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private  MoviesAdapter moviesAdapter;
    private ArrayList<MoviesDb> movies = new ArrayList<>();
    private Button refreshbutton;
    private  static final String MOVIE_KEY = "movie";
    private static final String MOVIE_FAV_SELECTED = "favorite";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isNetworkAvailable()){
            setContentView(R.layout.offline_layout);
            refreshbutton = (Button) findViewById(R.id.refreshbutton);
            refreshbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if (isNetworkAvailable())
                       startApp("top");
                   else {

                   }
                }
            });
        }
        else{
            startApp("top");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                startApp("popular");
                break;
            case R.id.action_toprated:
                startApp("top");
                break;
            case R.id.favorites:
                startFavorites();




        }
        return super.onOptionsItemSelected(item);
    }

    private void startApp(String sortby){

        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.moviesgridview);
        moviesAdapter = new MoviesAdapter(this,movies);
        moviesAdapter.clear();
        final RequestQueue queue = Volley.newRequestQueue(this);

        TheMovieDbClient.getJsonString(queue,this,moviesAdapter,sortby);
        gridView.setAdapter(moviesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MoviesDb movie = moviesAdapter.getItem(position);
                String trialersrequest = TheMovieDbClient.getVideosRequestUrl(movie.getID());

              String reviewsurl =  TheMovieDbClient.reviewsUrl(movie.getID());

              TheMovieDbClient.getReviewsResponse(getApplicationContext(),queue,reviewsurl);

                TheMovieDbClient.getTrailersKey(queue,getApplicationContext(),trialersrequest);

                Intent i = new Intent(MainActivity.this,MovieDetailsActivity.class);
               i.putExtra(MOVIE_KEY, Parcels.wrap(movie));
                //Toast.makeText(getApplicationContext(),movie.getTitle(),Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });

    }


   public void startFavorites() {

        Cursor cursor = getContentResolver().query(FavoritemoviesTable.CONTENT_URI,
                null, null, null, null);
        final List<FavoriteMovies> favouriteList = FavoritemoviesTable.getRows(cursor, false);


        if (!favouriteList.isEmpty()) {


            setContentView(R.layout.activity_main);

            gridView = (GridView) findViewById(R.id.moviesgridview);
            FavoriteMoviesAdapter favoriteMoviesAdapter = new FavoriteMoviesAdapter(getApplicationContext(),
                    favouriteList);
            gridView.setAdapter(favoriteMoviesAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FavoriteMovies favoriteMovies = favouriteList.get(position);
                    Intent intent = new Intent(getApplicationContext(),MovieDetailsActivity.class);
                    intent.putExtra(MOVIE_KEY,Parcels.wrap(favoriteMovies));
                    intent.putExtra(MOVIE_FAV_SELECTED,true);

                    startActivity(intent);

                }
            });

            }
            else
                Toast.makeText(getApplicationContext(),"Empty favorite list",Toast.LENGTH_SHORT).show();
    }



    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }



}
