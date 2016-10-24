package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.ConfigurationResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieListResponse;

/**
 * Created by user on 10/23/16.
 */

public class MovieDbService {

    public static final String API_SCHEME = "https";
    public static final String API_HOST = "api.themoviedb.org";
    public static final String API_VERSION = "3";

    private ConfigurationResponse configuration;
    private OkHttpClient client = new OkHttpClient();

    private String apiKey;

    private static MovieDbService instance;

    public static MovieDbService getInstance() {
        if(MovieDbService.instance == null) {
            MovieDbService.instance = new MovieDbService("b959f345d0c229f69dc5928e9bd24918");
        }
        return  MovieDbService.instance;
    }

    private MovieDbService(String apiKey) {
        this.apiKey = apiKey;
    }

    public MovieListResponse getMostPopularMovies() throws IOException {
        Request request = new Request.Builder()
                .url(getUrlBuilder()
                        .addPathSegment("discover")
                        .addPathSegment("movie")
                        .addQueryParameter("sort_by", "popularity.desc")
                        .build()
                )
                .build();

        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        MovieListResponse movieList =  gson.fromJson(response.body().string(), MovieListResponse.class);
        movieList.setConfiguration(getConfiguration());
        return movieList;
    }

    public ConfigurationResponse getConfiguration() {
        if(configuration == null) {
            try {
                configuration = downloadConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configuration;
    }

    private ConfigurationResponse downloadConfiguration() throws IOException {
        Request request = new Request.Builder()
                .url(getUrlBuilder()
                        .addPathSegment("configuration")
                        .build()
                )
                .build();

        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        return gson.fromJson(response.body().string(), ConfigurationResponse.class);
    }

    private HttpUrl.Builder getUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme(MovieDbService.API_SCHEME)
                .host(MovieDbService.API_HOST)
                .addQueryParameter("api_key", apiKey)
                .addPathSegment(MovieDbService.API_VERSION);
    }
}
