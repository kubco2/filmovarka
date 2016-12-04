package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.ConfigurationResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieListResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/23/16.
 */

public interface MovieDbService {

    String API_SCHEME = "http";
    String API_HOST = "api.themoviedb.org";
    String API_VERSION = "3";
    String API_KEY = "b959f345d0c229f69dc5928e9bd24918";

    @GET("movie/{id}?api_key=" + MovieDbService.API_KEY)
    public Call<MovieResponse> getMovie(@Path("id") int id);

    @GET("discover/movie?sort_by=popularity.desc&api_key=" + MovieDbService.API_KEY)
    public Call<MovieListResponse> getMostPopularMovies();

    @GET("discover/movie?sort_by=release_date.desc&api_key=" + MovieDbService.API_KEY)
    public Call<MovieListResponse> getNewMovies();

    @GET("discover/movie?sort_by=vote_count.desc&api_key=" + MovieDbService.API_KEY)
    public Call<MovieListResponse> getMostVotedMovies();

    @GET("configuration?api_key=" + MovieDbService.API_KEY)
    public Call<ConfigurationResponse> getConfiguration();

}
