package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 10/4/16.
 */

public class Movie implements Parcelable {

    long releaseDate = 1;
    String coverPath ="/";
    String title;
    String backdrop = "";
    float popularity = 1;

    public Movie(long releaseDate, String coverPath, String title, String backdrop, float popularity) {
        this.releaseDate = releaseDate;
        this.coverPath = coverPath;
        this.title = title;
        this.backdrop = backdrop;
        this.popularity = popularity;
    }

    private Movie(Parcel in) {
        String[] strings = new String[3];
        in.readStringArray(strings);
        this.coverPath = strings[0];
        this.title = strings[1];
        this.backdrop = strings[2];
        this.releaseDate = in.readLong();
        this.popularity = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.coverPath,
                this.title,
                this.backdrop});
        dest.writeLong(this.releaseDate);
        dest.writeFloat(this.popularity);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
