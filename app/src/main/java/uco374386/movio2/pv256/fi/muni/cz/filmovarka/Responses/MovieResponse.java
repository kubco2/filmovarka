package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MovieDbService;

/**
 * Created by user on 10/23/16.
 */

public class MovieResponse implements Parcelable {

    private ConfigurationResponse configuration;

    @SerializedName("poster_path")
    public String coverPath;
    public boolean adult;
    public String overview;
    @SerializedName("release_date")
    public Date releaseDate;
    @SerializedName("genre_ids")
    public int[] genreIds;
    public int id;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("original_language")
    public String originalLanguage;
    public String title;
    @SerializedName("backdrop_path")
    public String backdropPath;
    public float popularity;
    @SerializedName("vote_count")
    public int voteCount;
    public boolean video;
    @SerializedName("vote_average")
    public double voteAverage;

    public void setConfiguration(ConfigurationResponse configuration) {
        this.configuration = configuration;
    }

    public String getPosterUrl(String size) {
        return MovieDbService.getInstance().getConfiguration().images.secureBaseUrl +
                ((size != null) ? size : "original") + coverPath;
    }

    public String getBackdropUrl(String size) {
        return MovieDbService.getInstance().getConfiguration().images.secureBaseUrl +
                ((size != null) ? size : "original") + backdropPath;
    }

    public String getMostSuitableBackdropUrl(Context context) {
        return MovieDbService.getInstance().getConfiguration().images.secureBaseUrl +
                configuration.getMostSuitableBackdropSize(context) +
                backdropPath;
    }

    private MovieResponse(Parcel in) {
        String[] strings = new String[3];
        in.readStringArray(strings);
        this.coverPath = strings[0];
        this.title = strings[1];
        this.backdropPath = strings[2];
        this.releaseDate = new Date(in.readLong());
        this.popularity = in.readFloat();
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
                this.backdropPath
        });
        dest.writeLong(this.releaseDate.getTime());
        dest.writeFloat(this.popularity);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };
}
