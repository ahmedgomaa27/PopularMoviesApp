package com.example.ahmedhamdy.popularmoviesapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ahmed hamdy on 12/22/2017.
 */

/*
public String title;
    public int voteAverage;
    public String posterPath;
    public String overView;
    public String realeseDate;
    public int movieId;
*/


public class MoviesDataBaseHelper extends SQLiteOpenHelper {

    // database info
    private static final String DATABASE_NAME = "moviesDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_MOVIES = "movies";
    // User Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VOTE_AVERAGE = "vote";
    private static final String KEY_POSTER_PATH = "path";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_RELEASE_DATE = "releaseDate";
    private static final String KEY_MOVIE_ID = "movieId";


    public MoviesDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CRETAE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + // define primary key
                      KEY_TITLE +" TEXT," +
                      KEY_VOTE_AVERAGE + " INTEGER,"+
                      KEY_POSTER_PATH + " TEXT,"+
                      KEY_OVERVIEW + " TEXT," +
                      KEY_RELEASE_DATE + " TEXT," +
                      KEY_MOVIE_ID +" TEXT " + ")";

        db.execSQL(CRETAE_MOVIES_TABLE);


    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Simplest implementation is to drop all old tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);



    }
}
