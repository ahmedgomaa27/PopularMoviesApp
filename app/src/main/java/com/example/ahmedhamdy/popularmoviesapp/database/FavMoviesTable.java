package com.example.ahmedhamdy.popularmoviesapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.ahmedhamdy.popularmoviesapp.MoviesDb;

import org.sqlite.database.sqlite.SQLiteDatabase;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;

/**
 * Created by ahmed hamdy on 12/18/2017.
 */

public class FavMoviesTable extends BaseTable<MoviesDb> {

    public static final Table<MoviesDb> TABLE = new FavMoviesTable();
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String VOTEAVERAGE = "voteAverage";
    public static final String POSTERPATH = "posterpath";
    public static final String OVERVIEW = "overview";
    public static final String REALESEDATE = "realesedate";
    public static final String MOVIEID = "movieId";


    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this).intColumn(ID)
                .textColumn(TITLE)
                .intColumn(VOTEAVERAGE)
                .textColumn(POSTERPATH)
                .textColumn(OVERVIEW)
                .textColumn(REALESEDATE)
                .intColumn(MOVIEID)
                .execute(database);

    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull MoviesDb moviesDb) {
        ContentValues values = new ContentValues();
        values.put(ID, moviesDb.getId());
        values.put(TITLE, moviesDb.getTitle());
        values.put(VOTEAVERAGE, moviesDb.getVoteAverage());
        values.put(POSTERPATH, moviesDb.getPosterPath());
        values.put(OVERVIEW, moviesDb.getOverview());
        values.put(REALESEDATE, moviesDb.getRealeseDate());
        values.put(MOVIEID, moviesDb.getMovieId());
        return values;
    }

    @NonNull
    @Override
    public MoviesDb fromCursor(@NonNull Cursor cursor) {

        MoviesDb movie = new MoviesDb();
        movie.id = cursor.getInt(cursor.getColumnIndex(ID));
        movie.title = cursor.getString(cursor.getColumnIndex(TITLE));
        movie.voteAverage = cursor.getInt(cursor.getColumnIndex(VOTEAVERAGE));
        movie.posterPath = cursor.getString(cursor.getColumnIndex(POSTERPATH));
        movie.overview = cursor.getString(cursor.getColumnIndex(OVERVIEW));
        movie.realeseDate = cursor.getString(cursor.getColumnIndex(REALESEDATE));
        movie.movieId = cursor.getInt(cursor.getColumnIndex(MOVIEID));
        return movie;

    }
}
