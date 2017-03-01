package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.test.AndroidTestCase;

import java.util.Date;
import java.util.List;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbContract;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbManager;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 12/2/16.
 */

public class MovieDbManagerTest extends AndroidTestCase {
    private static final String TAG = MovieDbManagerTest.class.getSimpleName();

    private MovieDbManager mManager;

    @Override
    protected void setUp() throws Exception {
        mManager = new MovieDbManager(mContext);
        mContext.getContentResolver().delete(
                MovieDbContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
    }

    @Override
    public void tearDown() throws Exception {
        mContext.getContentResolver().delete(
                MovieDbContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
    }

    public void testGetCreateMovie() throws Exception{
        MovieResponse movie = createMovie();
        mManager.createMovie(movie);
        MovieResponse movieSaved = mManager.getMovie(movie.movieDbId);
        assertNotNull("movie not saved? it is not found or getMovie doesnt work", movieSaved);
        assertEquals("movie saved not consistent", movie.movieDbId, movieSaved.movieDbId);
        assertEquals("movie saved not consistent", movie.voteAverage, movieSaved.voteAverage);
        assertEquals("movie saved not consistent", movie.title, movieSaved.title);
        assertEquals("movie saved not consistent", movie.imageBasePath, movieSaved.imageBasePath);
        assertEquals("movie saved not consistent", movie.backdropPath, movieSaved.backdropPath);
        assertEquals("movie saved not consistent", movie.coverPath, movieSaved.coverPath);
        assertEquals("movie saved not consistent", movie.popularity, movieSaved.popularity);
        assertEquals("movie saved not consistent", movie.overview, movieSaved.overview);
    }

    public void testDeleteMovie() throws Exception{
        MovieResponse movie = createMovie();
        mManager.createMovie(movie);
        movie = mManager.getMovie(movie.movieDbId);
        assertNotNull("movie not saved? it is not found or getMovie doesnt work", movie);
        mManager.deleteMovie(movie);
        movie = mManager.getMovie(movie.movieDbId);
        assertNull("movie not deleted", movie);
    }

    public void testUpdateMovie() throws Exception{
        MovieResponse movie = createMovie();
        mManager.createMovie(movie);
        movie = mManager.getMovie(movie.movieDbId);
        movie.voteAverage=1f;
        movie.title="1";
        movie.imageBasePath="1";
        movie.backdropPath="1";
        movie.coverPath="1";
        movie.popularity=1f;
        movie.overview="1";
        mManager.updateMovie(movie);
        MovieResponse movieSaved = mManager.getMovie(movie.movieDbId);
        assertNotNull("movie not saved? it is not found or getMovie doesnt work", movie);
        assertEquals("movie updated not consistent", movie.movieDbId, movieSaved.movieDbId);
        assertEquals("movie updated not consistent", movie.voteAverage, movieSaved.voteAverage);
        assertEquals("movie updated not consistent", movie.title, movieSaved.title);
        assertEquals("movie updated not consistent", movie.imageBasePath, movieSaved.imageBasePath);
        assertEquals("movie updated not consistent", movie.backdropPath, movieSaved.backdropPath);
        assertEquals("movie updated not consistent", movie.coverPath, movieSaved.coverPath);
        assertEquals("movie updated not consistent", movie.popularity, movieSaved.popularity);
        assertEquals("movie updated not consistent", movie.overview, movieSaved.overview);
    }

    public void testGetAllMovies() throws Exception{
        MovieResponse movie1 = createMovie();
        MovieResponse movie2 = createMovie();
        MovieResponse movie3 = createMovie();
        movie2.movieDbId=1;
        movie3.movieDbId=2;
        mManager.createMovie(movie1);
        mManager.createMovie(movie2);
        mManager.createMovie(movie3);
        List<MovieResponse> list = mManager.getAll();
        assertEquals("not all movies saved or getAll doesnt work", 3, list.size());
        assertEquals("not all movies saved or getAll doesnt work", list.get(0).movieDbId, movie1.movieDbId);
        assertEquals("not all movies saved or getAll doesnt work", list.get(1).movieDbId, movie2.movieDbId);
        assertEquals("not all movies saved or getAll doesnt work", list.get(2).movieDbId, movie3.movieDbId);
    }

    public void testChangeState() throws Exception{
        MovieResponse movie1 = createMovie();
        MovieResponse movieSaved = mManager.getMovie(movie1.movieDbId);
        assertNull("movie should not be saved or getMovie doesnt work right", movieSaved);
        mManager.changeSaveState(movie1, false);
        movieSaved = mManager.getMovie(movie1.movieDbId);
        assertNull("movie should not be saved or getMovie/changeSaveState doesnt work right", movieSaved);
        mManager.changeSaveState(movie1, true);
        movieSaved = mManager.getMovie(movie1.movieDbId);
        assertNotNull("movie should be saved or getMovie/changeSaveState doesnt work right", movieSaved);
        mManager.changeSaveState(movie1, false);
        movieSaved = mManager.getMovie(movie1.movieDbId);
        assertNull("movie should not be saved or getMovie/changeSaveState doesnt work right", movieSaved);
    }

    public MovieResponse createMovie() {
        MovieResponse movie = new MovieResponse();
        movie.movieDbId = 259316;
        movie.voteAverage = 7.1f;
        movie.title = "Fantastic Beasts and Where to Find Them";
        movie.imageBasePath = "https://image.tmdb.org/t/p/";
        movie.backdropPath = "/6I2tPx6KIiBB4TWFiWwNUzrbxUn.jpg";
        movie.coverPath = "/gri0DDxsERr6B2sOR1fGLxLpSLx.jpg";
        movie.popularity = 54.581436f;
        movie.releaseDate = new Date();
        movie.overview = "In 1926, Newt Scamander arrives at the Magical Congress of the United States of America with a magically expanded briefcase, which houses a number of dangerous creatures and their habitats. When the creatures escape from the briefcase, it sends the American wizarding authorities after Newt, and threatens to strain even further the state of magical and non-magical relations.";
        return movie;
    }
}
