package com.example.ahmedhamdy.popularmoviesapp.database;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by ahmed hamdy on 12/12/2017.
 */


@SimpleSQLConfig(
        name = "MoviesProvider",
        authority = "com.example.ahmedhamdy.popularmoviesapp.authority",
        database = "favoritemovies.db",
        version = 1)

public class MoviesProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
