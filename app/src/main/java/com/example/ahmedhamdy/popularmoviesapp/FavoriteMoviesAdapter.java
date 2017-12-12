package com.example.ahmedhamdy.popularmoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ahmedhamdy.popularmoviesapp.database.FavoriteMovies;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ahmed hamdy on 12/12/2017.
 */

public class FavoriteMoviesAdapter extends ArrayAdapter<FavoriteMovies> {

    public FavoriteMoviesAdapter(Context context, List<FavoriteMovies> favoriteMovies) {
        super(context,0, favoriteMovies);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        FavoriteMovies favoriteMovies = getItem(position);
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movies_item,parent,false);
        }
        ImageView imageview = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        Picasso.with(getContext()).setLoggingEnabled(true);
        Picasso.with(getContext()).load(favoriteMovies.posterPath).into(imageview);

        return convertView;
    }
}
