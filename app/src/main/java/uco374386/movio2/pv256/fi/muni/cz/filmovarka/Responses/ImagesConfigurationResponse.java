package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 10/23/16.
 */

public class ImagesConfigurationResponse {

    @SerializedName("base_url")
    public String baseUrl;
    @SerializedName("secure_base_url")
    public String secureBaseUrl;
    @SerializedName("backdrop_sizes")
    public String[] backdropSizes;
    @SerializedName("logo_sizes")
    public String[] logoSizes;
    @SerializedName("poster_sizes")
    public String[] posterSizes;
    @SerializedName("profile_sizes")
    public String[] profileSizes;
    @SerializedName("still_sizes")
    public String[] stillSizes;


}
