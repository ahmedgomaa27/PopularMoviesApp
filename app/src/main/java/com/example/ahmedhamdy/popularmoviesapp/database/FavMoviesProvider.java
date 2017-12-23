package com.example.ahmedhamdy.popularmoviesapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ahmed hamdy on 12/23/2017.
 */

public class FavMoviesProvider extends ContentProvider {

    // Use an int for each URI we will run, this represents the different queries
    private static final int MOVIE = 200;
    private static final int MOVIE_ID = 201;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDataBaseHelper mOpenHelper;



    @Override
    public boolean onCreate() {
        mOpenHelper = new MoviesDataBaseHelper(getContext());
        return true;
    }

    /**
     * Builds a UriMatcher that is used to determine witch database request is being made.
     */
    public static UriMatcher buildUriMatcher(){
        String content = MovieContract.CONTENT_AUTHORITY;
        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, MovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(content, MovieContract.PATH_MOVIE + "/#", MOVIE_ID);

        return matcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
            case MOVIE:
                retCursor = db.query(
                        MovieContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MOVIE_ID:
             long  _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        MovieContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        MovieContract.MovieEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set the notification URI for the cursor to the one passed into the function. This
        // causes the cursor to register a content observer to watch for changes that happen to
        // this URI and any of it's descendants. By descendants, we mean any URI that begins
        // with this path.
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(sUriMatcher.match(uri)){
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch(sUriMatcher.match(uri)){
            case MOVIE:
                _id = db.insert(MovieContract.MovieEntry.TABLE_MOVIES, null, values);
                if(_id > 0){
                    returnUri = MovieContract.MovieEntry.buildMovieUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
