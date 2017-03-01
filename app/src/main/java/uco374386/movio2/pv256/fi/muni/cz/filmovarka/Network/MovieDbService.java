package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Network;

import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.BuildConfig;
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

    @GET("movie/{id}")
    public Call<MovieResponse> getMovie(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("discover/movie?sort_by=popularity.desc")
    public Call<MovieListResponse> getMostPopularMovies(@Query("without_genres") String withoutGenres, @Query("api_key") String apiKey);

    @GET("discover/movie?sort_by=release_date.desc")
    public Call<MovieListResponse> getNewMovies(@Query("without_genres") String withoutGenres, @Query("api_key") String apiKey);

    @GET("discover/movie?sort_by=vote_count.desc")
    public Call<MovieListResponse> getMostVotedMovies(@Query("without_genres") String withoutGenres, @Query("api_key") String apiKey);

    @GET("configuration")
    public Call<ConfigurationResponse> getConfiguration(@Query("api_key") String apiKey);

}
