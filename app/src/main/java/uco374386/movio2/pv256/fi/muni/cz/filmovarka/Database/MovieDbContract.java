package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.BuildConfig;

/**
 * Created by user on 12/4/16.
 */

public class MovieDbContract {
    public static final String CONTENT_AUTHORITY = BuildConfig.AUTHORITY;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIEDB_ID = "moviedb_id";
        public static final String COLUMN_TITLE_TEXT = "title";
        public static final String COLUMN_POPULARITY_TEXT = "popularity";
        public static final String COLUMN_VOTE_TEXT = "vote";
        public static final String COLUMN_RELEASE_DATE_TEXT = "release_date";
        public static final String COLUMN_COVER_PATH_TEXT = "cover_path";
        public static final String COLUMN_BACKDROP_PATH_TEXT = "poster_path";
        public static final String COLUMN_IMAGE_BASE = "image_base";
        public static final String COLUMN_OVERVIEW_TEXT = "overview";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
