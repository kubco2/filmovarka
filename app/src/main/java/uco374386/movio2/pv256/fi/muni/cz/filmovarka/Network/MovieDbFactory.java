package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 10/26/16.
 */

public class MovieDbFactory {

    private static MovieDbService movieDbService;

    public static MovieDbService getMovieDbService() {
        if(movieDbService == null) {

            Retrofit retrofit= new Retrofit.Builder()
                    .baseUrl(MovieDbService.API_SCHEME + "://" + MovieDbService.API_HOST + "/" + MovieDbService.API_VERSION + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            movieDbService = retrofit.create(MovieDbService.class);
        }
        return movieDbService;
    }
}
