package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Network;

import retrofit2.Call;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.BuildConfig;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.ConfigurationResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieListResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 12/5/16.
 */

public class MovieDbManager {
    private String key = BuildConfig.MOVIEDB_API_KEY;
    private MovieDbService service = MovieDbFactory.getMovieDbService();

    public MovieDbManager() {
    }

    public Call<MovieResponse> getMovie(int id) {
        return service.getMovie(id, key);
    }

    public Call<MovieListResponse> getMostPopularMovies(String withoutGenres) {
        return service.getMostPopularMovies(withoutGenres, key);
    }

    public Call<ConfigurationResponse> getConfiguration() {
        return service.getConfiguration(key);
    }

    public Call<MovieListResponse> getNewMovies(String withoutGenres) {
        return service.getNewMovies(withoutGenres, key);
    }

    public Call<MovieListResponse> getMostVotedMovies(String withoutGenres) {
        return service.getMostVotedMovies(withoutGenres, key);
    }
}
