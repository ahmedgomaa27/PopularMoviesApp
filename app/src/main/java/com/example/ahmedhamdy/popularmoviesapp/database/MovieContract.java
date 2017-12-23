package com.example.ahmedhamdy.popularmoviesapp.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ahmed hamdy on 12/23/2017.
 */

public class MovieContract {

    /**
     * The Content Authority is a name for the entire content provider, similar to the relationship
     * between a domain name and its website. A convenient string to use for content authority is
     * the package name for the app, since it is guaranteed to be unique on the device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.ahmedhamdy.popularmoviesapp.moviedatabase";

    /**
     * The content authority is used to create the base of all URIs which apps will use to
     * contact this content provider.
     */
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * A list of possible paths that will be appended to the base URI for each of the different
     * tables.
     */
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        // Table Names
        public static final String TABLE_MOVIES = "movies";
        // User Table Columns
        public static final String KEY_TITLE = "title";
        public static final String KEY_VOTE_AVERAGE = "vote";
        public static final String KEY_POSTER_PATH = "path";
        public static final String KEY_OVERVIEW = "overview";
        public static final String KEY_RELEASE_DATE = "releaseDate";
        public static final String KEY_MOVIE_ID = "movieId";

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_MOVIE;


        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
