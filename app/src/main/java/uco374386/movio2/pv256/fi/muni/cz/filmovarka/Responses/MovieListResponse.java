package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 10/23/16.
 */

public class MovieListResponse {

    public int page;
    private MovieResponse[] results;
    @SerializedName("total_results")
    public int totalResults;
    @SerializedName("total_pages")
    public int totalPages;

    private ConfigurationResponse configuration = null;

    public void setConfiguration(ConfigurationResponse configuration) {
        this.configuration = configuration;
    }

    public void setItems(List<MovieResponse> movies) {
        results = movies.toArray(new MovieResponse[movies.size()]);
    }

    public MovieResponse[] getResults() {
        if(configuration == null) {
            throw new IllegalStateException("Configuration is null");
        }
        if(results == null) {
            return new MovieResponse[0];
        };
        for(MovieResponse item: results) {
            item.imageBasePath = configuration.images.secureBaseUrl;
        }
        return results;
    }
}
