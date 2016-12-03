package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.ConfigurationResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieListResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/26/16.
 */

public class DownloadService extends IntentService {
    public static final String TAG = DownloadService.class.getSimpleName();
    public static final String DOWNLOAD_SERVICE_INTENT = "uco374386.movio2.pv256.fi.muni.cz.filmovarka.DOWNLOAD_SERVICE";
    public static final String EXTRA_ACTION = "action";
    public static final String EXTRA_RESPONSE = "response";
    public static final String EXTRA_RESPONSE_ERROR = "response_error";
    public static final String RESPONSE_ERROR_OFFLINE = "response_error.offline";
    public static final String RESPONSE_ERROR_PARSE = "response_error.parse";
    public static final String ACTION_DOWNLOAD_LIST_MOST_POPULAR = "getMostPopularMovies";
    public static final String ACTION_DOWNLOAD_LIST_MOST_VOTED = "getMostVotedMovies";

    private MovieDbService service;
    private ConfigurationResponse serverConfiguration;

    public DownloadService() {
        super(TAG);
    }

    public DownloadService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        service = MovieDbFactory.getMovieDbService();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        showDownloadStartNotification();
        try {
            if(serverConfiguration == null) {
                serverConfiguration = service.getConfiguration().execute().body();
            }
            switch (action) {
                case ACTION_DOWNLOAD_LIST_MOST_POPULAR:
                    broadcastMovies(service.getMostPopularMovies().execute().body(), action);
                    break;
                case ACTION_DOWNLOAD_LIST_MOST_VOTED:
                    broadcastMovies(service.getMostVotedMovies().execute().body(), action);
                    break;
                default:
                    throw new IllegalArgumentException("action not recognized");
            }
        } catch (MalformedJsonException e) {
            broadcastMovieListError(RESPONSE_ERROR_PARSE);
            e.printStackTrace();
            return;
        } catch (IOException e) {
            broadcastMovieListError(RESPONSE_ERROR_OFFLINE);
            e.printStackTrace();
            return;
        }
        showDownloadFinishNotification();
    }

    private void broadcastMovies(MovieListResponse movieList, String action) {
        if(movieList == null) {
            return;
        }
        Intent intent = new Intent(DOWNLOAD_SERVICE_INTENT);
        intent.putExtra(EXTRA_ACTION, action);
        movieList.setConfiguration(serverConfiguration);
        intent.putParcelableArrayListExtra(EXTRA_RESPONSE,
                new ArrayList<MovieResponse>(Arrays.asList(movieList.getResults())));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void broadcastMovieListError(String error) {
        Intent intent = new Intent(DOWNLOAD_SERVICE_INTENT);
        intent.putExtra(EXTRA_RESPONSE_ERROR, error);
        showErrorNotification(error);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void showErrorNotification(String error) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder n  = new Notification.Builder(this)
                .setContentTitle(getResources().getText(R.string.app_name))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent) //přesměruje nás do aplikace
                .setAutoCancel(true);

        switch (error) {
            case RESPONSE_ERROR_OFFLINE:
                n.setContentText(getResources().getText(R.string.no_internet_connection));
                break;
            case RESPONSE_ERROR_PARSE:
                n.setContentText(getResources().getText(R.string.parse_error));
                break;
        }
        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build()); //0 udává číslo notifikace. Na některých zařízeních nefunguje jinačí int než 0.
    }

    private void showDownloadStartNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder n  = new Notification.Builder(this)
                .setContentTitle(getResources().getText(R.string.app_name))
                .setContentText(getResources().getText(R.string.download_start))
                .setSmallIcon(R.drawable.ic_ondemand_video_black_24dp)
                .setContentIntent(pIntent) //přesměruje nás do aplikace
                .setAutoCancel(true);
        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build()); //0 udává číslo notifikace. Na některých zařízeních nefunguje jinačí int než 0.
    }

    private void showDownloadFinishNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder n  = new Notification.Builder(this)
                .setContentTitle(getResources().getText(R.string.app_name))
                .setContentText(getResources().getText(R.string.download_finish))
                .setSmallIcon(R.drawable.ic_ondemand_video_black_24dp)
                .setContentIntent(pIntent) //přesměruje nás do aplikace
                .setAutoCancel(true);
        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build()); //0 udává číslo notifikace. Na některých zařízeních nefunguje jinačí int než 0.
    }
}
