package com.example.ahmedhamdy.popularmoviesapp.database;

import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.SQLiteConfig;
import ru.arturvasilov.sqlite.core.SQLiteContentProvider;
import ru.arturvasilov.sqlite.core.SQLiteSchema;

/**
 * Created by ahmed hamdy on 12/18/2017.
 */

public class FavMoviesProvider extends SQLiteContentProvider {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final String CONTENT_AUTHORITY = "com.myapp";

    @Override
    protected void prepareConfig(@NonNull SQLiteConfig config) {
        config.setDatabaseName(DATABASE_NAME);
        config.setAuthority(CONTENT_AUTHORITY);

    }

    @Override
    protected void prepareSchema(@NonNull SQLiteSchema schema) {
        schema.register(FavMoviesTable.TABLE);

    }
}
