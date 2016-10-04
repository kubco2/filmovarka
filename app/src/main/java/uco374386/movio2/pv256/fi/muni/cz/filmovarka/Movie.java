package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

/**
 * Created by user on 10/4/16.
 */

public class Movie {
    private String mName;
    private Double mRating = 0.0;

    public Movie(String name, Double rating) {
        mName = name;
        mRating = rating;
    }

    public String getName() {
        return mName;
    }

    public Double getRating() {
        return mRating;
    }
}
