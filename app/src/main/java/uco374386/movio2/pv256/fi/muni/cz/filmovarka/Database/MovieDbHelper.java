package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 11/29/16.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";
    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static Date getDateFromDb(String dateText) throws ParseException {
        return new SimpleDateFormat(DB_DATE_FORMAT).parse(dateText);
    }

    public static String getDateString(Date date) {
        return new SimpleDateFormat(DB_DATE_FORMAT).format(date);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + MovieResponse.MovieEntry.TABLE_NAME + " (" +
                MovieResponse.MovieEntry._ID + " LONG PRIMARY KEY," +
                MovieResponse.MovieEntry.COLUMN_MOVIEDB_ID + " INTEGER NOT NULL, " +
                MovieResponse.MovieEntry.COLUMN_TITLE_TEXT + " TEXT NOT NULL," +
                MovieResponse.MovieEntry.COLUMN_RELEASE_DATE_TEXT + " TEXT NOT NULL," +
                MovieResponse.MovieEntry.COLUMN_VOTE_TEXT + " DOUBLE NOT NULL," +
                MovieResponse.MovieEntry.COLUMN_POPULARITY_TEXT + " DOUBLE NOT NULL," +
                MovieResponse.MovieEntry.COLUMN_BACKDROP_PATH_TEXT + " TEXT NOT NULL," +
                MovieResponse.MovieEntry.COLUMN_COVER_PATH_TEXT + " TEXT NOT NULL," +
                "UNIQUE (" + MovieResponse.MovieEntry.COLUMN_MOVIEDB_ID + ") ON CONFLICT REPLACE" +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieResponse.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
