package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 11/29/16.
 */

public class MovieDbManager {

    public static final int COL_MOVIE_ID = 0;
    public static final int COL_MOVIE_MOVIEDB_ID = 1;
    public static final int COL_MOVIE_TITLE = 2;
    public static final int COL_MOVIE_RELEASE_DATE = 3;
    public static final int COL_MOVIE_VOTE = 4;
    public static final int COL_MOVIE_POPULARITY = 5;
    public static final int COL_MOVIE_BACKDROP_PATH = 6;
    public static final int COL_MOVIE_COVER_PATH = 7;
    public static final int COL_MOVIE_IMAGE_BASE = 8;
    public static final int COL_MOVIE_OVERVIEW = 9;
    private static final String[] MOVIE_COLUMNS = {
            MovieDbContract.MovieEntry._ID,
            MovieDbContract.MovieEntry.COLUMN_MOVIEDB_ID,
            MovieDbContract.MovieEntry.COLUMN_TITLE_TEXT,
            MovieDbContract.MovieEntry.COLUMN_RELEASE_DATE_TEXT,
            MovieDbContract.MovieEntry.COLUMN_VOTE_TEXT,
            MovieDbContract.MovieEntry.COLUMN_POPULARITY_TEXT,
            MovieDbContract.MovieEntry.COLUMN_BACKDROP_PATH_TEXT,
            MovieDbContract.MovieEntry.COLUMN_COVER_PATH_TEXT,
            MovieDbContract.MovieEntry.COLUMN_IMAGE_BASE,
            MovieDbContract.MovieEntry.COLUMN_OVERVIEW_TEXT
    };

    private static final String WHERE_MOVIEDB_ID = MovieDbContract.MovieEntry.COLUMN_MOVIEDB_ID + " = ?";
    //private static final String WHERE_DAY = MovieEntry.COLUMN_END_DATE_TEXT + " IS NOT NULL AND substr(" + MovieEntry.COLUMN_START_DATE_TEXT + ",1,8) = ? OR " + "substr(" + MovieEntry.COLUMN_END_DATE_TEXT + ",1,8) = ?";
    //private static final String WHERE_DATES = MovieEntry.COLUMN_END_DATE_TEXT + " IS NOT NULL AND substr(" + MovieEntry.COLUMN_END_DATE_TEXT + ",1,8) >= ? AND " + "substr(" + MovieEntry.COLUMN_START_DATE_TEXT + ",1,8) <= ?";

    private Context mContext;

    public MovieDbManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public void createMovie(MovieResponse movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.movieDbId == null) {
            throw new IllegalStateException("movie movieDbId cannot be null");
        }
        if (movie.title == null) {
            throw new IllegalStateException("movie title cannot be null");
        }
        if (movie.releaseDate == null) {
            throw new IllegalStateException("movie releaseDate cannot be null");
        }
        if (movie.voteAverage == null) {
            throw new IllegalStateException("movie voteAverage cannot be null");
        }
        if (movie.popularity == null) {
            throw new IllegalStateException("movie voteAverage cannot be null");
        }
        if (movie.backdropPath == null) {
            throw new IllegalStateException("movie backdropPath cannot be null");
        }
        if (movie.coverPath == null) {
            throw new IllegalStateException("movie coverPath cannot be null");
        }
        if (movie.imageBasePath == null) {
            throw new IllegalStateException("movie imageBasePath cannot be null");
        }
        if (movie.overview == null) {
            throw new IllegalStateException("movie overview cannot be null");
        }
        movie.localDbId = ContentUris.parseId(mContext.getContentResolver().insert(MovieDbContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie)));
    }

    public void updateMovie(MovieResponse movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.movieDbId == null) {
            throw new IllegalStateException("movie movieDbId cannot be null");
        }
        if (movie.title == null) {
            throw new IllegalStateException("movie title cannot be null");
        }
        if (movie.releaseDate == null) {
            throw new IllegalStateException("movie releaseDate cannot be null");
        }
        if (movie.voteAverage == null) {
            throw new IllegalStateException("movie voteAverage cannot be null");
        }
        if (movie.popularity == null) {
            throw new IllegalStateException("movie voteAverage cannot be null");
        }
        if (movie.backdropPath == null) {
            throw new IllegalStateException("movie backdropPath cannot be null");
        }
        if (movie.coverPath == null) {
            throw new IllegalStateException("movie coverPath cannot be null");
        }
        if (movie.imageBasePath == null) {
            throw new IllegalStateException("movie imageBasePath cannot be null");
        }
        if (movie.overview == null) {
            throw new IllegalStateException("movie overview cannot be null");
        }
        mContext.getContentResolver().update(MovieDbContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie), WHERE_MOVIEDB_ID, new String[]{String.valueOf(movie.movieDbId)});
    }

    public void deleteMovie(MovieResponse movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.movieDbId == null) {
            throw new IllegalStateException("movie movieDbId cannot be null");
        }

        mContext.getContentResolver().delete(MovieDbContract.MovieEntry.CONTENT_URI, WHERE_MOVIEDB_ID, new String[]{String.valueOf(movie.movieDbId)});
    }

    public MovieResponse getMovie(Integer movieDbId) {
        if (movieDbId == null) {
            throw new NullPointerException("movieDbId == null");
        }

        Cursor cursor = mContext.getContentResolver().query(MovieDbContract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, WHERE_MOVIEDB_ID, new String[]{movieDbId.toString()}, null);
        if (cursor != null) {
            if(cursor.moveToFirst()) {
                try {
                    return getMovie(cursor);
                } finally {
                    cursor.close();
                }
            }
            cursor.close();
        }
        return null;
    }

    public void changeSaveState(MovieResponse movie, boolean saved) {
        if(saved) {
            createMovie(movie);
        } else {
            deleteMovie(movie);
        }
    }

    public List<MovieResponse> getAll() {
        Cursor cursor = mContext.getContentResolver().query(MovieDbContract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, null, null, null);
        if (cursor != null) {
            if(cursor.moveToFirst()) {
                List<MovieResponse> movies = new ArrayList<>(cursor.getCount());
                try {
                    while (!cursor.isAfterLast()) {
                        movies.add(getMovie(cursor));
                        cursor.moveToNext();
                    }
                } finally {
                    cursor.close();
                }
                return movies;
            }
            cursor.close();
        }
        return Collections.emptyList();
    }

    private ContentValues prepareMovieValues(MovieResponse movie) {
        ContentValues values = new ContentValues();
        values.put(MovieDbContract.MovieEntry.COLUMN_MOVIEDB_ID, movie.movieDbId);
        values.put(MovieDbContract.MovieEntry.COLUMN_TITLE_TEXT, movie.title);
        values.put(MovieDbContract.MovieEntry.COLUMN_RELEASE_DATE_TEXT, MovieDbHelper.getDateString(movie.releaseDate));
        values.put(MovieDbContract.MovieEntry.COLUMN_VOTE_TEXT, movie.voteAverage);
        values.put(MovieDbContract.MovieEntry.COLUMN_POPULARITY_TEXT, movie.popularity);
        values.put(MovieDbContract.MovieEntry.COLUMN_BACKDROP_PATH_TEXT, movie.backdropPath);
        values.put(MovieDbContract.MovieEntry.COLUMN_COVER_PATH_TEXT, movie.coverPath);
        values.put(MovieDbContract.MovieEntry.COLUMN_IMAGE_BASE, movie.imageBasePath);
        values.put(MovieDbContract.MovieEntry.COLUMN_OVERVIEW_TEXT, movie.overview);
        return values;
    }

    private MovieResponse getMovie(Cursor cursor) {
        MovieResponse movie = new MovieResponse();
        movie.localDbId = cursor.getLong(COL_MOVIE_ID);
        movie.movieDbId = cursor.getInt(COL_MOVIE_MOVIEDB_ID);
        movie.title = cursor.getString(COL_MOVIE_TITLE);
        try {
            movie.releaseDate = MovieDbHelper.getDateFromDb(cursor.getString(COL_MOVIE_RELEASE_DATE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        movie.voteAverage = cursor.getFloat(COL_MOVIE_VOTE);
        movie.popularity = cursor.getFloat(COL_MOVIE_POPULARITY);
        movie.backdropPath = cursor.getString(COL_MOVIE_BACKDROP_PATH);
        movie.coverPath = cursor.getString(COL_MOVIE_COVER_PATH);
        movie.imageBasePath = cursor.getString(COL_MOVIE_IMAGE_BASE);
        movie.overview = cursor.getString(COL_MOVIE_OVERVIEW);
        return movie;
    }
    
}
