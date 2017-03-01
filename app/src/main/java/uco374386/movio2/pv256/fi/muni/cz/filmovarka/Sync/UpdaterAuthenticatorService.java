package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Sync.UpdaterAuthenticator;

/**
 * Created by user on 12/3/16.
 */

public class UpdaterAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private UpdaterAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new UpdaterAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
