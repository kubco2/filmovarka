package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by user on 12/3/16.
 */

public class UpdaterSyncListener extends BroadcastReceiver{
    public static final String TAG = UpdaterSyncListener.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_BOOT_COMPLETED:
            case Intent.ACTION_MY_PACKAGE_REPLACED:
                Log.i(TAG, "Setup sync alarms ACTION_BOOT_COMPLETED ACTION_MY_PACKAGE_REPLACED");
                //UpdaterSyncAdapter.configurePeriodicSync(context, UpdaterSyncAdapter.SYNC_INTERVAL, UpdaterSyncAdapter.SYNC_FLEXTIME);
                UpdaterSyncAdapter.configurePeriodicSync(context, 60, 20);
        }
    }
}
