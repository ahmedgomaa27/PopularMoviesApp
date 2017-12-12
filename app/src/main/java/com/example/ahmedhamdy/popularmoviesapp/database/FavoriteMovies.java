package com.example.ahmedhamdy.popularmoviesapp.database;

import org.parceler.Parcel;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by ahmed hamdy on 12/12/2017.
 */

@Parcel
@SimpleSQLTable(table = "favoritemovies",provider = "MoviesProvider")
public class FavoriteMovies {

    @SimpleSQLColumn("col_str")
    public String title;
    @SimpleSQLColumn("col_integar")
    public int voteAverage;
    @SimpleSQLColumn("col_str2")
    public String posterPath;
    @SimpleSQLColumn("col_str3")
    public String overview;
    @SimpleSQLColumn("col_str4")
    public String realeseDate;


    public FavoriteMovies(){

    }

}
