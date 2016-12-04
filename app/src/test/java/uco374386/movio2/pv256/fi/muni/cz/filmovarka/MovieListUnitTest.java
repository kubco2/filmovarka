package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.ConfigurationResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.ImagesConfigurationResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieListResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

import static org.junit.Assert.*;

public class MovieListUnitTest {

    @Test
    public void testMovieListResponse() throws Exception {

        MovieListResponse list = new MovieListResponse();
        ConfigurationResponse config = new ConfigurationResponse();
        config.images = new ImagesConfigurationResponse();
        config.images.secureBaseUrl = "https://example.com/images/";

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

        list.setConfiguration(config);
        assertEquals("getResults should return empty array", list.getResults().length, 0);
        List<MovieResponse> list1 = new ArrayList<>();
        list1.add(movie1);
        list1.add(movie2);
        list.setItems(list1);
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