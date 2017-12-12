package com.example.ahmedhamdy.popularmoviesapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedhamdy.popularmoviesapp.database.FavoriteMovies;
import com.example.ahmedhamdy.popularmoviesapp.database.FavoritemoviesTable;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by ahmed hamdy on 10/9/2017.
 */

public class MovieDetailsActivity extends AppCompatActivity{


    TextView titleview;
    ImageView movieImage;
    TextView movie_overview;
    TextView voteAverage;
    TextView releaseDate;
    private  static final String MOVIE_KEY = "movie";
    private static final String MOVIE_FAV_SELECTED = "favorite";
    ArrayList links = TheMovieDbClient.getLinksfinal();
    ListView trialerslist ;
    ArrayAdapter listadapter;
    ListView reviewslist;
    ArrayAdapter reviewsadapter;
    ArrayList<String> reviews = new ArrayList<>();
    Button favouritebutton;
    TextView trialertitle,reviewstitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_details);

        titleview = (TextView) findViewById(R.id.movie_title_textview);
        movieImage = (ImageView) findViewById(R.id.movie_image);
        movie_overview = (TextView) findViewById(R.id.movie_review);
        voteAverage = (TextView) findViewById(R.id.voteaverage);
        releaseDate = (TextView) findViewById(R.id.releasedate);
        trialerslist = (ListView) findViewById(R.id.trialerslistview);
        reviewslist = (ListView) findViewById(R.id.reviewslist);
        favouritebutton = (Button) findViewById(R.id.favouritebutton);
        trialertitle = (TextView) findViewById(R.id.trailersTextview);
        reviewstitle = (TextView) findViewById(R.id.reviewstextview);


        boolean isFavSelected = getIntent().getBooleanExtra(MOVIE_FAV_SELECTED,false);
        setMovieDetails(isFavSelected);






    }
    public void setMovieDetails(boolean favselected) {
        if (!favselected)
        {
            final MoviesDb movie = (MoviesDb) Parcels.unwrap(getIntent().getParcelableExtra(MOVIE_KEY));
        titleview.setText(movie.getTitle());
        Picasso.with(getApplicationContext()).load(movie.getPosterPath()).into(movieImage);
        movie_overview.setText(movie.getOverview());
        voteAverage.setText(String.valueOf(movie.getVoteAverage() + "/10"));
        releaseDate.setText(movie.getRealeseDate());
            trialerslist.setVisibility(View.VISIBLE);
            reviewslist.setVisibility(View.VISIBLE);
            favouritebutton.setVisibility(View.VISIBLE);
            reviewstitle.setVisibility(View.VISIBLE);
            trialertitle.setVisibility(View.VISIBLE);


            setTrailer(trialerslist);
        setReviews(reviewslist);


        favouritebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToDataBase(movie);

            }
        });

    }
    else {
            FavoriteMovies favoriteMovies = Parcels.unwrap(getIntent().getParcelableExtra(MOVIE_KEY));
            titleview.setText(favoriteMovies.title);
            Picasso.with(getApplicationContext()).load(favoriteMovies.posterPath).into(movieImage);
            movie_overview.setText(favoriteMovies.overview);
            voteAverage.setText(String.valueOf(favoriteMovies.voteAverage)+"/10");
            releaseDate.setText(favoriteMovies.realeseDate);
            trialerslist.setVisibility(View.GONE);
            reviewslist.setVisibility(View.GONE);
            favouritebutton.setVisibility(View.GONE);
            reviewstitle.setVisibility(View.GONE);
            trialertitle.setVisibility(View.GONE);


        }


    }

    private void setTrailer(ListView gridView){
        ArrayList<String> words = new ArrayList<>(links.size());
        for (int i =0;i<links.size();i++){
            words.add("Trailer "+ String.valueOf(i+1));
        }

        listadapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,words);
        gridView.setAdapter(listadapter);
        setListViewHeightBasedOnChildren(gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = links.get(position).toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

    }
    private void setReviews(ListView reviewslist){

        reviews.clear();
         reviews = TheMovieDbClient.getReviewsarray();



        reviewsadapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,
               reviews );

        reviewslist.setAdapter(reviewsadapter);
        setListViewHeightBasedOnChildren(reviewslist);
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public void addToDataBase(MoviesDb movie){

       FavoriteMovies favoriteMovies = new FavoriteMovies();
        favoriteMovies.title = movie.getTitle();
        favoriteMovies.overview = movie.getOverview();
        favoriteMovies.posterPath = movie.getPosterPath();
        favoriteMovies.realeseDate = movie.getRealeseDate();
        favoriteMovies.voteAverage = movie.getVoteAverage();



        getContentResolver().insert(FavoritemoviesTable.CONTENT_URI,FavoritemoviesTable.getContentValues(
                favoriteMovies,true
        ));

        Toast.makeText(getApplicationContext(),"Added To Favorite List",Toast.LENGTH_LONG).show();


               /* Cursor cursor = getContentResolver().query(FavoritemoviesTable.CONTENT_URI,
                        null,null,null,null);
                FavoriteMovies temp = FavoritemoviesTable.getRow(cursor,true);
                Toast.makeText(getApplicationContext(),temp.title,Toast.LENGTH_LONG).show();
                */


    }


}