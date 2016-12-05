package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

/**
 * Created by user on 12/5/16.
 */

public class Logger {
    public static void i(String tag, String string) {
        if (BuildConfig.LOGGING) android.util.Log.i(tag, string);
    }
    public static void e(String tag, String string) {
        if (BuildConfig.LOGGING) android.util.Log.e(tag, string);
    }
    public static void d(String tag, String string) {
        if (BuildConfig.LOGGING) android.util.Log.d(tag, string);
    }
    public static void v(String tag, String string) {
        if (BuildConfig.LOGGING) android.util.Log.v(tag, string);
    }
    public static void w(String tag, String string) {
        if (BuildConfig.LOGGING) android.util.Log.w(tag, string);
    }
}
