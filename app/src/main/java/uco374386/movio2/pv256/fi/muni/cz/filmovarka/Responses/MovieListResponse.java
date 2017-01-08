package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 10/23/16.
 */

public class MovieListResponse {

    public int page;
    public MovieResponse[] results;
    @SerializedName("total_results")
    public int totalResults;
    @SerializedName("total_pages")
    public int totalPages;

}
