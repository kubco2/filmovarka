package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by user on 12/3/16.
 */

public class UpdaterSyncService extends Service {

    private static final Object LOCK = new Object();
    private static UpdaterSyncAdapter sUpdaterSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (LOCK) {
            if (sUpdaterSyncAdapter == null) {
                sUpdaterSyncAdapter = new UpdaterSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sUpdaterSyncAdapter.getSyncAdapterBinder();
    }
}