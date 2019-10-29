package com.tbfajri.moviecatalog.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns {

        public static String NAME = "name";
        public static String DESCRIPTION = "description";
        public static String RELEASE_DATE = "date";
        public static String PHOTO = "photo";
        public static String VOTE_COUNT = "vote_count";
        public static String POPULARITY = "popularity";

    }
}
