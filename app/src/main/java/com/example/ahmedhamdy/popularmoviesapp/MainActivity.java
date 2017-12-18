package com.example.ahmedhamdy.popularmoviesapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.example.ahmedhamdy.popularmoviesapp.database.FavMoviesTable;

import org.parceler.Parcels;

import java.util.ArrayList;

import ru.arturvasilov.sqlite.core.SQLite;

public class MainActivity extends AppCompatActivity {
    private static final String MOVIE_KEY = "movie";
    private static final String CURRENT_SELECTION = "index";
    private static final String CURRENT_SORT = "sort";
    int index = 1;
    private GridView gridView;
    private MoviesAdapter moviesAdapter;
    private ArrayList<MoviesDb> movies = new ArrayList<>();
    private Button refreshButton;
    private String sortedBY = "top";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_SORT, sortedBY);
        index = gridView.getFirstVisiblePosition();
        outState.putInt(CURRENT_SELECTION, index);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.moviesgridview);

        if (savedInstanceState == null) {
            SQLite.initialize(getApplicationContext());

            startApp(sortedBY);
        } else {
            sortedBY = savedInstanceState.getString(CURRENT_SORT);
            index = savedInstanceState.getInt(CURRENT_SELECTION);
            // Toast.makeText(getApplicationContext(),String.valueOf(index),Toast.LENGTH_SHORT).show();
            if (sortedBY.equals("fav")) {
                startFavorites();
            } else {
                initStart(sortedBY);

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                startApp("popular");
                sortedBY = "popular";
                Toast.makeText(getApplicationContext(), sortedBY, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_toprated:
                startApp("top");
                sortedBY = "top";
                Toast.makeText(getApplicationContext(), sortedBY, Toast.LENGTH_SHORT).show();
                break;
            case R.id.favorites:
                startFavorites();
                sortedBY = "fav";


        }
        return super.onOptionsItemSelected(item);
    }

    private void startApp(String sortby) {


        moviesAdapter = new MoviesAdapter(this, movies);
        moviesAdapter.clear();
        final RequestQueue queue = Volley.newRequestQueue(this);

        TheMovieDbClient.getJsonString(queue, this, moviesAdapter, sortby);
        gridView.setAdapter(moviesAdapter);
        if (index != 1) {

            gridView.setSelection(index);
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MoviesDb movie = moviesAdapter.getItem(position);
                String trialersrequest = TheMovieDbClient.getVideosRequestUrl(movie.getMovieId());

                String reviewsurl = TheMovieDbClient.reviewsUrl(movie.getMovieId());

                TheMovieDbClient.getReviewsResponse(getApplicationContext(), queue, reviewsurl);

                TheMovieDbClient.getTrailersKey(queue, getApplicationContext(), trialersrequest);

                Intent i = new Intent(MainActivity.this, MovieDetailsActivity.class);
                i.putExtra(MOVIE_KEY, Parcels.wrap(movie));
                //Toast.makeText(getApplicationContext(),movie.getTitle(),Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });

    }


    public void startFavorites() {


        movies.clear();
        movies.addAll(SQLite.get().query(FavMoviesTable.TABLE));

        moviesAdapter = new MoviesAdapter(this, movies);
        gridView.setAdapter(moviesAdapter);
        gridView.setSelection(index);


    }


    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    public void initStart(final String sort) {
        if (!isNetworkAvailable()) {
            setContentView(R.layout.offline_layout);
            refreshButton = (Button) findViewById(R.id.refreshbutton);
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        if (!TextUtils.isEmpty(sort))
                            startApp(sort);
                        else
                            startApp(sort);
                    } else {

                    }
                }
            });
        } else {
            if (!TextUtils.isEmpty(sort))
                startApp(sort);
            else
                startApp("popular");
        }

    }

}
