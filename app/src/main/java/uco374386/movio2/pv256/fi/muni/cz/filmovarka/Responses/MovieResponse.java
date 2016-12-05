package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses;

import android.content.ContentUris;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbContract;

/**
 * Created by user on 10/23/16.
 */

public class MovieResponse implements Parcelable, Cloneable {

    public static final String DATE_FORMAT = "yyyy-MM-dd";


    @SerializedName("poster_path")
    public String coverPath;
    public String overview;
    @SerializedName("release_date")
    public Date releaseDate;
    @SerializedName("id")
    public Integer movieDbId;
    public Long localDbId;
    public String title;
    @SerializedName("backdrop_path")
    public String backdropPath;
    public Float popularity;
    @SerializedName("vote_average")
    public Float voteAverage;
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
        String[] strings = new String[5];
        in.readStringArray(strings);
        this.coverPath = strings[0];
        this.title = strings[1];
        this.backdropPath = strings[2];
        this.imageBasePath = strings[3];
        this.overview = strings[4];
        this.releaseDate = new Date(in.readLong());
        this.popularity = in.readFloat();
        this.movieDbId = in.readInt();
        this.voteAverage = in.readFloat();
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
                this.imageBasePath,
                this.overview
        });
        dest.writeLong(this.releaseDate.getTime());
        dest.writeFloat(    this.popularity);
        dest.writeInt(this.movieDbId);
        dest.writeFloat(this.voteAverage);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieResponse that = (MovieResponse) o;

        if (coverPath != null ? !coverPath.equals(that.coverPath) : that.coverPath != null)
            return false;
        if (overview != null ? !overview.equals(that.overview) : that.overview != null)
            return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null)
            return false;
        if (movieDbId != null ? !movieDbId.equals(that.movieDbId) : that.movieDbId != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (backdropPath != null ? !backdropPath.equals(that.backdropPath) : that.backdropPath != null)
            return false;
        if (popularity != null ? !popularity.equals(that.popularity) : that.popularity != null)
            return false;
        if (voteAverage != null ? !voteAverage.equals(that.voteAverage) : that.voteAverage != null)
            return false;
        return imageBasePath != null ? imageBasePath.equals(that.imageBasePath) : that.imageBasePath == null;

    }

    @Override
    public int hashCode() {
        int result = coverPath != null ? coverPath.hashCode() : 0;
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (movieDbId != null ? movieDbId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (backdropPath != null ? backdropPath.hashCode() : 0);
        result = 31 * result + (popularity != null ? popularity.hashCode() : 0);
        result = 31 * result + (voteAverage != null ? voteAverage.hashCode() : 0);
        result = 31 * result + (imageBasePath != null ? imageBasePath.hashCode() : 0);
        return result;
    }

    public MovieResponse createClone() {
        try {
            return (MovieResponse) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
