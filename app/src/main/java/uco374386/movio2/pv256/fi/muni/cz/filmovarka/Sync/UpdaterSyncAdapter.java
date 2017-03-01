package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbManager;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MainActivity;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Network.MovieDbFactory;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Network.MovieDbService;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.R;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.ConfigurationResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by user on 12/3/16.
 */

public class UpdaterSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = UpdaterSyncAdapter.class.getSimpleName();
    // Interval at which to sync with the server, in seconds.
    public static final int SYNC_INTERVAL = 60 * 60 * 24; //day
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    public UpdaterSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder()
                    .syncPeriodic(syncInterval, flexTime)
                    .setSyncAdapter(account, authority)
                    .setExtras(Bundle.EMPTY) //enter non null Bundle, otherwise on some phones it crashes sync
                    .build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one if the
     * fake account doesn't exist yet.  If we make a new account, we call the onAccountCreated
     * method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        Log.i(TAG, "onAccountCreated - sync configured");
        /*
         * Since we've created an account
         */
        UpdaterSyncAdapter.configurePeriodicSync(context, 60, 20);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i(TAG, "onPerformSync");
        boolean hasChanges = false;
        MovieDbManager mManager = new MovieDbManager(getContext());
        MovieDbService mService = MovieDbFactory.getMovieDbService();
        List<MovieResponse> list = mManager.getAll();
        if(list.isEmpty()) {
            return;
        }
        ConfigurationResponse configuration;
        try {
            configuration = mService.getConfiguration().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        for(MovieResponse movie: list) {
            MovieResponse movieDownloaded = null;
            try {
                Response<MovieResponse> response = mService.getMovie(movie.movieDbId).execute();
                if(response.code() == 404) {
                    mManager.deleteMovie(movie);
                    hasChanges = true;
                } else if(response.code() == 200) {
                    movieDownloaded = response.body();
                    movieDownloaded.imageBasePath = configuration.images.secureBaseUrl;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(movieDownloaded == null || movieDownloaded.equals(movie)) {
                continue;
            }
            hasChanges = true;

            mManager.updateMovie(movieDownloaded);
        }
        if(!hasChanges) {
            return;
        }

        Intent intent = new Intent(this.getContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this.getContext(), 0, intent, 0);

        Notification.Builder n  = new Notification.Builder(this.getContext())
                .setContentTitle(getContext().getResources().getText(R.string.app_name))
                .setContentText(getContext().getResources().getText(R.string.sync_changes))
                .setSmallIcon(R.drawable.ic_ondemand_video_black_24dp)
                .setContentIntent(pIntent)
                .setAutoCancel(true);
        NotificationManager notificationManager =  (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build());
    }
}