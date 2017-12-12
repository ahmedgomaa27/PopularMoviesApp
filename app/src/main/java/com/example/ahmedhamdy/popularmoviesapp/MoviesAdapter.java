package com.example.ahmedhamdy.popularmoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmed hamdy on 10/1/2017.
 */

public class MoviesAdapter extends ArrayAdapter<MoviesDb> {


    public MoviesAdapter(Context context, ArrayList<MoviesDb> moviesDb) {
        super(context,0,moviesDb);

    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {


        MoviesDb movie = getItem(position);

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movies_item,parent,false);
        }
        ImageView imageview = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        Picasso.with(getContext()).setLoggingEnabled(true);
        Picasso.with(getContext()).load(movie.getPosterPath()).into(imageview);

        return convertView;
    }
}
