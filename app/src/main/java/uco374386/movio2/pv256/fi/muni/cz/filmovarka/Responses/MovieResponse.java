package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MovieDbService;

/**
 * Created by user on 10/23/16.
 */

public class MovieResponse implements Parcelable {

    public static final String CONTENT_AUTHORITY = "uco374386.movio2.pv256.fi.muni.cz.filmovarka.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";
    public static final String DATE_FORMAT = "yyyy-MM-dd";


    @SerializedName("poster_path")
    public String coverPath;
    public boolean adult;
    public String overview;
    @SerializedName("release_date")
    public Date releaseDate;
    @SerializedName("genre_ids")
    public int[] genreIds;
    @SerializedName("id")
    public Integer movieDbId;
    public Long localDbId;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("original_language")
    public String originalLanguage;
    public String title;
    @SerializedName("backdrop_path")
    public String backdropPath;
    public Double popularity;
    @SerializedName("vote_count")
    public int voteCount;
    public boolean video;
    @SerializedName("vote_average")
    public Double voteAverage;
    public String imageBasePath;

    public MovieResponse() {

    }

    public String getPosterUrl(String size) {
        return imageBasePath +
                ((size != null) ? size : "original") + coverPath;
    }

    public String getBackdropUrl(String size) {
        return imageBasePath +
                ((size != null) ? size : "original") + backdropPath;
    }

    public MovieResponse(Parcel in) {
        String[] strings = new String[4];
        in.readStringArray(strings);
        this.coverPath = strings[0];
        this.title = strings[1];
        this.backdropPath = strings[2];
        this.imageBasePath = strings[3];
        this.releaseDate = new Date(in.readLong());
        this.popularity = in.readDouble();
        this.movieDbId = in.readInt();
        this.voteAverage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.coverPath,
                this.title,
                this.backdropPath,
                this.imageBasePath
        });
        dest.writeLong(this.releaseDate.getTime());
        dest.writeDouble(this.popularity);
        dest.writeLong(this.movieDbId);
        dest.writeDouble(this.voteAverage);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

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

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
