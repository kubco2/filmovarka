package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.ConfigurationResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieListResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class MoviesUnitTest {

    @Test
    public void testMovieCreateBackdropUrlOriginal() throws Exception {
        MovieResponse movie1 = new MovieResponse();
        movie1.imageBasePath = "https://example.com/images/";
        movie1.backdropPath = "/backdrop.jpg";
        String backdropOriginalUrl1 = "https://example.com/images/original/backdrop.jpg";
        assertEquals(backdropOriginalUrl1, movie1.getBackdropUrl(null));
    }

    @Test
    public void testMovieCreateBackdropUrlSized() throws Exception {
        MovieResponse movie1 = new MovieResponse();
        movie1.imageBasePath = "https://example.com/images/";
        movie1.backdropPath = "/backdrop.jpg";
        String backdropSizedUrl1 = "https://example.com/images/w1280/backdrop.jpg";
        assertEquals(backdropSizedUrl1, movie1.getBackdropUrl("w1280"));
    }

    @Test
    public void testMovieCreateCoverUrlOriginal() throws Exception {
        MovieResponse movie1 = new MovieResponse();
        movie1.imageBasePath = "https://example.com/images/";
        movie1.coverPath = "/cover.jpg";
        String coverOriginalUrl1 = "https://example.com/images/original/cover.jpg";
        assertEquals(coverOriginalUrl1, movie1.getPosterUrl(null));
    }

    @Test
    public void testMovieCreateCoverUrlSized() throws Exception {
        MovieResponse movie1 = new MovieResponse();
        movie1.imageBasePath = "https://example.com/images/";
        movie1.coverPath = "/cover.jpg";
        String coverSizedUrl1 = "https://example.com/images/w1280/cover.jpg";
        assertEquals(coverSizedUrl1, movie1.getPosterUrl("w1280"));
    }

    @Test
    public void testMovieEqualForSync() throws Exception {
        MovieResponse movie1 = new MovieResponse();
        movie1.releaseDate = new Date();
        movie1.movieDbId = 1;
        movie1.title = "test";
        movie1.popularity = 1f;
        movie1.voteAverage = 1f;
        movie1.overview = "test";
        movie1.backdropPath = "test";
        movie1.imageBasePath = "https://example.com/images/";
        movie1.coverPath = "/cover.jpg";
        MovieResponse movie2 = movie1.createClone();
        assertEquals(movie1, movie2);
    }

    @Test
    public void testMovieNotEqualForSync() throws Exception {
        MovieResponse movie1 = new MovieResponse();
        movie1.releaseDate = new Date();
        movie1.movieDbId = 1;
        movie1.title = "test";
        movie1.popularity = 1f;
        movie1.voteAverage = 1f;
        movie1.overview = "test";
        movie1.backdropPath = "test";
        movie1.imageBasePath = "https://example.com/images/";
        movie1.coverPath = "/cover.jpg";
        MovieResponse movie2 = movie1.createClone();
        movie2.releaseDate = new Date();
        movie2.releaseDate.setTime(1L);
        assertNotEquals(movie1, movie2);
        movie2.releaseDate = movie1.releaseDate;
        assertEquals(movie1, movie2);
        movie2 = movie1.createClone();
        movie2.movieDbId = 2;
        assertNotEquals(movie1, movie2);
        movie2 = movie1.createClone();
        movie2.title = "custom";
        assertNotEquals(movie1, movie2);
        movie2 = movie1.createClone();
        movie2.popularity = 2f;
        assertNotEquals(movie1, movie2);
        movie2 = movie1.createClone();
        movie2.voteAverage = 2f;
        assertNotEquals(movie1, movie2);
        movie2 = movie1.createClone();
        movie2.overview = "custom";
        assertNotEquals(movie1, movie2);
        movie2 = movie1.createClone();
        movie2.backdropPath = "custom";
        assertNotEquals(movie1, movie2);
        movie2 = movie1.createClone();
        movie2.imageBasePath = "custom";
        assertNotEquals(movie1, movie2);
        movie2 = movie1.createClone();
        movie2.coverPath = "custom";
        assertNotEquals(movie1, movie2);
    }

    @Test
    public void testMovieListResponse() throws Exception {
        ConfigurationResponse config = Mockito.mock(ConfigurationResponse.class);
        when(config.getImageBasePath())
                .thenReturn("https://example.com/images/");
        MovieListResponse list = new MovieListResponse();
        list.setConfiguration(config);

        MovieResponse movie1 = new MovieResponse();
        movie1.backdropPath = "/backdrop.jpg";
        movie1.coverPath = "/cover.jpg";
        String coverOriginalUrl1 = "https://example.com/images/original/cover.jpg";
        String backdropOriginalUrl1 = "https://example.com/images/original/backdrop.jpg";
        String coverSizedUrl1 = "https://example.com/images/w1280/cover.jpg";
        String backdropSizedUrl1 = "https://example.com/images/w1280/backdrop.jpg";
        MovieResponse movie2 = new MovieResponse();
        movie2.backdropPath = "/backdrop1.jpg";
        movie2.coverPath = "/cover1.jpg";
        String coverOriginalUrl2 = "https://example.com/images/original/cover1.jpg";
        String backdropOriginalUrl2 = "https://example.com/images/original/backdrop1.jpg";
        String coverSizedUrl2 = "https://example.com/images/w1280/cover1.jpg";
        String backdropSizedUrl2 = "https://example.com/images/w1280/backdrop1.jpg";
        assertEquals("getResults should return empty array", list.getResults().length, 0);
        list.setItems(Arrays.asList(movie1, movie2));
        assertEquals("getResults should return two movies", list.getResults().length, 2);
        assertNotNull("imagePath empty", list.getResults()[0].imageBasePath);
        assertEquals(backdropOriginalUrl1, list.getResults()[0].getBackdropUrl(null));
        assertEquals(backdropSizedUrl1, list.getResults()[0].getBackdropUrl("w1280"));
        assertEquals(coverOriginalUrl1, list.getResults()[0].getPosterUrl(null));
        assertEquals(coverSizedUrl1, list.getResults()[0].getPosterUrl("w1280"));
        assertEquals(backdropOriginalUrl2, list.getResults()[1].getBackdropUrl(null));
        assertEquals(backdropSizedUrl2, list.getResults()[1].getBackdropUrl("w1280"));
        assertEquals(coverOriginalUrl2, list.getResults()[1].getPosterUrl(null));
        assertEquals(coverSizedUrl2, list.getResults()[1].getPosterUrl("w1280"));
    }
}